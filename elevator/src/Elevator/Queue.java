package Elevator;

import java.util.LinkedList;

class Queue <Integer>
{
	LinkedList<Integer> list = new LinkedList<Integer>();

	public int lastIndexOf(Integer item)
	{
		return list.lastIndexOf(item);
	}
	public Integer rearValue()
	{
		if(list.size() == 0)
			return null;
		return list.get(list.size()-1);
	}
	public int getSize()
	{
		return list.size();
	}
	
	// Return the first stored value in the current queue
	public Integer qPeek()  
	{
		if(qIsEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
	public void insertNormal(int idx, Integer item) 
	{
		list.add(idx, item);
	}
	public Integer getValue(int idx) 
	{
		return list.get(idx);
	}
	public void insertQ(Integer item) 
	{
		list.add(item);
	}
	
	public void deleteByIdx(int idx)
	{
		list.remove(idx);
	}
	public void deleteObject(Integer num)
	{
		list.remove(num);
	}
	public Integer deleteQ() 
	{
		if(qIsEmpty())
		{
			return null;
		}
		else
		{
			Integer temp = list.get(0);
			list.remove(0);
			return temp;
		}
	}
	
	public boolean qIsEmpty() 
	{
		if(list.size() == 0)
			return true;
		else
			return false;
	}
}

