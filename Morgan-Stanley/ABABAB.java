package com.mayank.playground;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MorganStanley {
    private static final ReentrantLock lock = new ReentrantLock();
    // there can be multiple conditions
    private static final Condition condition = lock.newCondition();

    private static boolean isATurn = true;

    static void main() {
        Thread threadA = new Thread(() -> {
            for(int i=0;i<3;i++){
                lock.lock();
                try{
                    /**
                     * not using if condition here, as there are chances that spurious signals can wake the thread.
                     * Once it gets the lock, it resumes from the line after await() —
                     * typically re-checking the condition in a while loop.
                     */
                    while (!isATurn){
                        condition.await(); // wakes up on receive of signal and continues the while loop for second time
                    }
                    System.out.print("A");
                    /**
                     * I'm able to access isATurn inside lambda even though it is not final/effectivelyFinal
                     * because that condition is only for local variables from enclosing scope, not for static/instance variables.
                     * Lambda copies variables by value and objects via reference copy, so if the value changes outside lambda then lambda can behave unpredictably.
                     * That's why this condition is there.
                     * Fields (static or instance) are part of the object’s state.
                     * They live as long as the object (or class, for static).
                     * Lambdas capture a reference to the object, not a copy of the field.
                     * So even if the field changes, the lambda sees the updated value — safely.
                     */
                    isATurn = false;
                    condition.signal(); //signalAll
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }

        });

        Thread threadB = new Thread(() -> {
            for(int i=0;i<3;i++){
                lock.lock();
                try{
                    /**
                     * not using if condition here, as there are chances that spurious signals can wake the thread.
                     * Once it gets the lock, it resumes from the line after await() —
                     * typically re-checking the condition in a while loop.
                     */
                    while (isATurn){
                        condition.await(); // wakes up on receive of signal and continues the while loop for second time
                    }
                    System.out.print("B");
                    /**
                     * I'm able to access isATurn inside lambda even though it is not final/effectivelyFinal
                     * because that condition is only for local variables from enclosing scope, not for static/instance variables.
                     * Lambda copies variables by value and objects via reference copy, so if the value changes outside lambda then lambda can behave unpredictably.
                     * That's why this condition is there.
                     * Fields (static or instance) are part of the object’s state.
                     * They live as long as the object (or class, for static).
                     * Lambdas capture a reference to the object, not a copy of the field.
                     * So even if the field changes, the lambda sees the updated value — safely.
                     */
                    isATurn = true;
                    condition.signal(); //signalAll
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });

        threadA.start();
        threadB.start();


    }

}
