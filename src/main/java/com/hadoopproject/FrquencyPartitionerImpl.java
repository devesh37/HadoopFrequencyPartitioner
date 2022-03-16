package com.hadoopproject;



import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class FrquencyPartitionerImpl extends Configured implements Tool{
 
    public int run(String[] args) throws Exception{

        if(args.length !=3){
            System.err.println("Invalid Command");
            System.err.println("Usage:<Range> <input path> <output path>");
            return -1;
        }
        Range r=new Range(args[0]);
        Job job = Job.getInstance(getConf());
        job.setJobName("customPartition");
        job.setJarByClass(FrquencyPartitionerImpl.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        FrequencyPartitioner.setRange(r);
        job.setPartitionerClass(FrequencyPartitioner.class);
        job.setNumReduceTasks(r.getTotalRanges());

        Path inputFilePath = new Path(args[1]);
        Path outputFilePath = new Path(args[2]);
        FileInputFormat.addInputPath(job, inputFilePath);
        FileOutputFormat.setOutputPath(job, outputFilePath);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        //range format "min-5,6-10,11-max"
    	int exitCode = ToolRunner.run(new FrquencyPartitionerImpl(), args);
        System.exit(exitCode);
    
    }
}


