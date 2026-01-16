package io.github.gaomingyuan666.base.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.github.gaomingyuan666.base.exception.BizException;
import io.github.gaomingyuan666.base.model.template.ConsumerTemplate;
import io.github.gaomingyuan666.base.model.template.LineFormatTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author gaomingyuan
 */
@Service
@Slf4j
public class BashService {
    private final LoadingCache<String, Object> LOCK_CACHE = Caffeine.newBuilder()
            .expireAfterAccess(60, TimeUnit.SECONDS).maximumSize(1000).build(key -> new Object());

    private final Object DEFAULT_LOCK = new Object();

    private Object getLock(String path) {
        Object lock = LOCK_CACHE.get(path);
        if (lock != null) {
            return lock;
        }
        return DEFAULT_LOCK;
    }

    public void resolve(String filePath, int batchSize, ConsumerTemplate fileResolveTemplate) throws Exception {
        synchronized (getLock(filePath)) {
            if (filePath.endsWith(".xlsx") || filePath.endsWith(".xls")) {
//                List<String> lines = new ArrayList<>();
//                ExcelReader build = EasyExcel.read(filePath).build();
//                List<ReadSheet> readSheets = build.excelExecutor().sheetList();
//                for (ReadSheet readSheet : readSheets) {
//                    EasyExcel.read(filePath, new AnalysisEventListener() {
//                        @Override
//                        public void invoke(Object o, AnalysisContext context) {
//                            Map<String, String> map = (Map<String, String>) o;
//                            List<String> list = new ArrayList<>(map.values());
//                            lines.add(StringUtils.join(list, "\t"));
//                            if (lines.size() == batchSize) {
//                                try {
//                                    fileResolveTemplate.accept(lines);
//                                } catch (Exception e) {
//                                    throw new RuntimeException(e);
//                                } finally {
//                                    lines.clear();
//                                }
//
//                            }
//                        }
//
//                        @Override
//                        public void invokeHeadMap(Map headMap, AnalysisContext context) {
//                            lines.add(StringUtils.join(new ArrayList<>(headMap.values()), "\t"));
//                        }
//
//                        @Override
//                        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                            if (lines.size() > 0) {
//                                try {
//                                    fileResolveTemplate.accept(lines);
//                                } catch (Exception e) {
//                                    throw new RuntimeException(e);
//                                } finally {
//                                    lines.clear();
//                                }
//                            }
//                        }
//                    }).sheet(readSheet.getSheetName()).doRead();
//                }
            } else {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                    int count = 0;
                    String line;
                    List<String> tmpList = new ArrayList<>();
                    while ((line = bufferedReader.readLine()) != null) {
                        count++;
                        tmpList.add(line);
                        if (count % batchSize == 0) {
                            if (Thread.currentThread().isInterrupted()) {
                                throw new BizException("thread is interrupted");
                            }
                            fileResolveTemplate.accept(tmpList);
                            tmpList.clear();
                        }

                    }
                    if (tmpList.size() > 0) {
                        fileResolveTemplate.accept(tmpList);
                    }
                }
            }

        }
    }

    public void write(String filePath, Collection<String> lines, LineFormatTemplate lineFormatTemplate) throws
            Exception {
        if (CollectionUtils.isEmpty(lines)) {
            return;
        }
        synchronized (getLock(filePath)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
                int count = 0;
                for (String line : lines) {
                    String result = lineFormatTemplate.format(line);
                    if (result == null) {
                        continue;
                    }
                    count++;
                    bufferedWriter.write(result);
                    if (count % 5000 == 0) {
                        bufferedWriter.flush();
                        if (Thread.currentThread().isInterrupted()) {
                            throw new BizException("thread is interrupted");
                        }
                    }
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
            }
        }
    }
}
