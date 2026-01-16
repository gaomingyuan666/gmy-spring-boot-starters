package io.github.gaomingyuan666.base.statemachine;

/**
 * @author gaomingyuan 
 */
public interface StateMachine<STATE, EVENT> {

    /**
     * 状态机转移
     */
    public STATE transition(STATE state, EVENT event);
}

