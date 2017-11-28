package com.otp.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class DataFactory<Type> {
	public abstract Type read(BufferedReader reader) throws IOException;
	public abstract void write(BufferedWriter writer, Type toWrite) throws IOException;
	public static DataFactory get(Class<?> returnType) {
		if(returnType.getName().equals("[Ljava.lang.String;")){
			return new DataFactory<String[]>(){
				@Override
				public String[] read(BufferedReader reader) throws IOException{
					String result = reader.readLine();
					return result.split(",");
				}

				@Override
				public void write(BufferedWriter writer, String[] toWrite) throws IOException {
					for(String string: toWrite){
						writer.append(string);
						writer.append(",");
					}
					writer.append("\n");
				}};
		}
		else if(returnType == String.class){
			return new DataFactory<String>(){
				@Override
				public String read(BufferedReader reader) throws IOException{
					String result = reader.readLine();
					if(result.endsWith(",")){
						return result.substring(0, result.length()-1);
					}
					else {
						return result;
					}
				}

				@Override
				public void write(BufferedWriter writer, String toWrite) throws IOException {
					writer.append(toWrite);
					writer.append(",\n");
				}};
		}
		else if(returnType.getName().equals("[I")){
			return new DataFactory<int[]>(){
				@Override
				public int[] read(BufferedReader reader) throws IOException{
					String[] entries = reader.readLine().split(",");
					int[] result = new int[entries.length];
					for(int i=0;i<entries.length;++i){
						result[i] = Integer.parseInt(entries[i]);
					}
					return result;
				}

				@Override
				public void write(BufferedWriter writer, int[] toWrite) throws IOException {
					for(int i: toWrite){
						writer.append(i+",");
					}
					writer.append("\n");
				}};
		}
		else if(returnType.getName().equals("[Ljava.lang.Integer;")){
			return new DataFactory<Integer[]>(){
				@Override
				public Integer[] read(BufferedReader reader) throws IOException{
					String[] entries = reader.readLine().split(",");
					Integer[] result = new Integer[entries.length];
					for(int i=0;i<entries.length;++i){
						result[i] = Integer.parseInt(entries[i]);
					}
					return result;
				}

				@Override
				public void write(BufferedWriter writer, Integer[] toWrite) throws IOException {
					for(Integer i: toWrite){
						writer.append(i+",");
					}
					writer.append("\n");
				}};
		}
		else if(returnType == int.class||returnType == Integer.class){
			return new DataFactory<Integer>(){
				@Override
				public Integer read(BufferedReader reader) throws IOException{
					String result = reader.readLine();
					if(result.endsWith(",")){
						return Integer.parseInt(result.substring(0, result.length()-1));
					}
					else {
						return Integer.parseInt(result);
					}
				}

				@Override
				public void write(BufferedWriter writer, Integer toWrite) throws IOException {
					writer.append(toWrite.toString());
					writer.append(",\n");
				}};
		}
		else if(returnType.getName().equals("[F")){
			return new DataFactory<float[]>(){
				@Override
				public float[] read(BufferedReader reader) throws IOException{
					String[] entries = reader.readLine().split(",");
					float[] result = new float[entries.length];
					for(int i=0;i<entries.length;++i){
						result[i] = Float.parseFloat(entries[i]);
					}
					return result;
				}

				@Override
				public void write(BufferedWriter writer, float[] toWrite) throws IOException {
					for(float i: toWrite){
						writer.append(i+",");
					}
					writer.append("\n");
				}};
		}
		else if(returnType.getName().equals("[Ljava.lang.Float;")){
			return new DataFactory<Float[]>(){
				@Override
				public Float[] read(BufferedReader reader) throws IOException{
					String[] entries = reader.readLine().split(",");
					Float[] result = new Float[entries.length];
					for(int i=0;i<entries.length;++i){
						result[i] = Float.parseFloat(entries[i]);
					}
					return result;
				}

				@Override
				public void write(BufferedWriter writer, Float[] toWrite) throws IOException {
					for(Float i: toWrite){
						writer.append(i+",");
					}
					writer.append("\n");
				}};
		}
		else if(returnType == float.class||returnType == Float.class){
			return new DataFactory<Float>(){
				@Override
				public Float read(BufferedReader reader) throws IOException{
					String result = reader.readLine();
					if(result.endsWith(",")){
						return Float.parseFloat(result.substring(0, result.length()-1));
					}
					else {
						return Float.parseFloat(result);
					}
				}

				@Override
				public void write(BufferedWriter writer, Float toWrite) throws IOException {
					writer.append(toWrite.toString());
					writer.append(",\n");
				}};
		}
		else if(returnType == List.class||returnType == ArrayList.class){
			return new DataFactory<ArrayList<?>>(){
				@Override
				public ArrayList<?> read(BufferedReader reader) throws IOException{
					String result = reader.readLine();
					if(result.endsWith(",")){
						result = result.substring(0, result.length()-1);
					}
					int indexOfFirstComma = result.indexOf(',');
					int indexOfSecondComma = result.indexOf(',',indexOfFirstComma+1);
					String dataType = result.substring(0,indexOfFirstComma);
					int width = Integer.parseInt(result.substring(indexOfFirstComma+1,indexOfSecondComma));
					int height = Integer.parseInt(result.substring(indexOfSecondComma+1));
					if("String".equals(dataType)){
						return DataFactory.readStringList(reader,width,height);
					}
					else if("Integer".equals(dataType)){
						return DataFactory.readIntegerList(reader,width,height);
					}
					else if("Float".equals(dataType)){
						return DataFactory.readFloatList(reader,width,height);
					}
					else if("None".equals(dataType)){
						return new ArrayList<Object>();
					}
					throw new RuntimeException("Unable to read List of type:"+dataType);
				}

				@Override
				public void write(BufferedWriter writer, ArrayList<?> toWrite) throws IOException {
					if(toWrite.isEmpty()){
						writer.append("None,0,0,");	
					}
					else {
						Object element = toWrite.get(0);
						int height = 1;
						int width = 0;
						if(element instanceof ArrayList){
							ArrayList<Object> list = (ArrayList<Object>)element;
							if(!list.isEmpty()){
								element = list.get(0);
								width = list.size();
								height = toWrite.size();
							}
							else {
								return;
							}
						}
						if(element instanceof Integer){
							writer.append("Integer,");
						}
						else if(element instanceof Float){
							writer.append("Float,");
						}
						else if(element instanceof String){
							writer.append("Float,");
						}
						writer.append(toWrite.size()+",");
						writer.append(height+",\n");
						if(height==1){
							for(int i=0;i<toWrite.size();++i){
								writer.append(toWrite.get(i)+",");
							}
						}
						else {
							for(int i=0;i<toWrite.size();++i){
								ArrayList<Object> subList = (ArrayList<Object>) toWrite.get(i);
								for(Object object:subList){
									writer.append(object.toString()+",");
								}
							}
						}
					}
					writer.append("\n");
				}};
		}
		return null;
	}
	protected static ArrayList readStringList(BufferedReader reader, int width, int height) throws IOException {
		if(height>1){
			ArrayList<List<String>> matrix = new ArrayList<List<String>>();
			for(int i=0;i<height;++i){
				String line = reader.readLine();
				List<String> list = new ArrayList<String>();
				for(String dataPoint: line.split(",")){
					list.add(dataPoint);
				}
				matrix.add(list);
			}
			return matrix;
		}
		else {
			String line = reader.readLine();
			ArrayList<String> list = new ArrayList<String>();
			for(String dataPoint: line.split(",")){
				list.add(dataPoint);
			}
			return list;
		}
	}
	protected static ArrayList readIntegerList(BufferedReader reader, int width, int height) throws IOException {
		if(height>1){
			ArrayList<List<Integer>> matrix = new ArrayList<List<Integer>>();
			for(int i=0;i<height;++i){
				String line = reader.readLine();
				List<Integer> list = new ArrayList<Integer>();
				for(String dataPoint: line.split(",")){
					list.add(Integer.parseInt(dataPoint));
				}
				matrix.add(list);
			}
			return matrix;
		}
		else {
			String line = reader.readLine();
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(String dataPoint: line.split(",")){
				list.add(Integer.parseInt(dataPoint));
			}
			return list;
		}
	}
	protected static ArrayList readFloatList(BufferedReader reader, int width, int height) throws IOException {
		if(height>1){
			ArrayList<List<Float>> matrix = new ArrayList<List<Float>>();
			for(int i=0;i<height;++i){
				String line = reader.readLine();
				List<Float> list = new ArrayList<Float>();
				for(String dataPoint: line.split(",")){
					list.add(Float.parseFloat(dataPoint));
				}
				matrix.add(list);
			}
			return matrix;
		}
		else {
			String line = reader.readLine();
			ArrayList<Float> list = new ArrayList<Float>();
			for(String dataPoint: line.split(",")){
				list.add(Float.parseFloat(dataPoint));
			}
			return list;
		}
	}
}
