package br.com.datawatcher.service;


public final class TimeWatcher {
    
    /** the total time in milliseconds */
    private long total;
    
    /** the start time in milliseconds */
    private long start;
    
    public TimeWatcher(){
    }
    
    /**
     * Starts the stop watch.
     */
    public TimeWatcher start() {
        start = System.currentTimeMillis();
        return this;
    }
    
    /**
     * Stops the stop watch.
     * @return the millis between the last start and current stop of this watch.
     */
    public TimeWatcher stop() {
    	long diff = System.currentTimeMillis() - start;
        total += diff;
        return this;
    }
    
    /**
     * Resets the stop watch to 0.
     */
    public void reset() {
        total = 0;
    }
    
    /**
     * @return returns the stopped total time in milliseconds.
     */
    public long getTime() {
        return total;
    }
}
