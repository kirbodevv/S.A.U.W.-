package com.KGC.SAUW;
import java.util.ArrayList;

public class crafting {
    public static class craft{
		int[] result;
		int[][] ingr;
		public craft(int[] result, int[][] ingr){
			this.result = result;
			this.ingr = ingr;
		}
	}
	public ArrayList<craft> crafts = new ArrayList<craft>();
	public void addCraft(craft craft){
		crafts.add(craft);
	}
	public void addCraft(int[] result, int[][] ingr){
		this.addCraft(new craft(result, ingr));
	}
}
