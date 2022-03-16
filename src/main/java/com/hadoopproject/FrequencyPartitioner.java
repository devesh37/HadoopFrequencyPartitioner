package com.hadoopproject;

/**
 * A custom Partitioner class which will assign words with count<10 to 1 reducer and the
 * rest to another reducer
 */


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FrequencyPartitioner extends Partitioner<IntWritable,Text> {
	static Range range;
	static void setRange(Range range)
	{
		FrequencyPartitioner.range=range;
	}
    @Override
    public int getPartition(IntWritable key, Text value, int numReduceTasks) {
        return range.getRangeBucket(Integer.parseInt(key.toString())) ;
    }
}
