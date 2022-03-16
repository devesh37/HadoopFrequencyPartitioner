package com.hadoopproject;

import java.util.ArrayList;

public class Range {
	private int totalRanges=0;
	private ArrayList<ArrayList<Integer>> rangeList=null;
	
	public Range(String str)
	{
		parseRanges(str);
	}
	public ArrayList<ArrayList<Integer>> getRangeList() {
		return rangeList;
	}
	public void setRangeList(ArrayList<ArrayList<Integer>> rangeList) {
		this.rangeList = rangeList;
	}
	public int getRangeBucket(int value)
	{
		if(rangeList==null||rangeList.size()==0)
			return 0;
		else
		{
			int i=0;
			for(ArrayList<Integer> range:rangeList)
			{
				Integer lowerBound=range.get(0);
				Integer upperBound=range.get(1);
				if(lowerBound==null)
				{
					if(value<=upperBound)
						return i;
				}
				else if(upperBound==null)
				{
					if(value>=lowerBound)
						return i;
				}
				else
				{
					if(value>=lowerBound&&value<=upperBound)
						return i;
						
				}
				i++;
			}
		}
		return 0;
	}
	
	public void parseRanges(String ranges)
	{
		System.out.println("Parsing:"+ranges);
		String[] rangeArg=ranges.split(",");
		rangeList=new ArrayList<ArrayList<Integer>>();
		totalRanges=rangeArg.length;
		for(String range: rangeArg)
		{
			String[] bounds=range.split("-");
			ArrayList<Integer> interval=new ArrayList<Integer>();
			
			if(bounds[0].equalsIgnoreCase("min"))
			{
				if(bounds[1].equalsIgnoreCase("max"))
				{
					totalRanges=1;
					rangeList.clear();
					break;
				}
				else
				{
					interval.add(null);
					interval.add(Integer.parseInt(bounds[1]));
				}
				
			}
			else
			{
				interval.add(Integer.parseInt(bounds[0]));
				
				if(bounds[1].equalsIgnoreCase("max"))
					interval.add(null);
				else
					interval.add(Integer.parseInt(bounds[1]));
				
			}
			
			rangeList.add(interval);
			
		}
	}

	public int getTotalRanges() {
		return totalRanges;
	}
}
