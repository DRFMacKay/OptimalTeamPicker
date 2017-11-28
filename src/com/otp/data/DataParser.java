package com.otp.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.otp.actor.Actor;

public class DataParser {

	private Map<String,DataFactory> returnTypes = new HashMap<String,DataFactory>();
	private Map<String,Method> setMethods = new HashMap<String,Method>();
	private Map<String,Method> getMethods = new HashMap<String,Method>();
	private List<String> variableNames = new ArrayList<String>();

	public DataParser(){
		for(Method method:Actor.class.getMethods()){
			if(method.getName().startsWith("get")&&!method.getName().equals("getClass")){
				String variableName = method.getName().substring(3);
				Class<?> returnType = method.getReturnType();
				returnTypes.put(variableName, DataFactory.get(returnType));
				getMethods.put(variableName, method);
				variableNames.add(variableName);
			}
			else if(method.getName().startsWith("set")){
				String variableName = method.getName().substring(3);
				setMethods.put(variableName, method);
			}
		}
		Collections.sort(variableNames);
	}

	public static List<Actor> read(String dataDirectoryName){
		return DataParser.read(new File(dataDirectoryName));
	}
	public static List<Actor> read(File dataDirectory){
		List<Actor> result = new ArrayList<Actor>();
		DataParser parser = new DataParser();
		for(String dataFileName: dataDirectory.list()) {
			int indexOfDot = dataFileName.lastIndexOf('.');
			if(indexOfDot > -1){
				if(dataFileName.substring(indexOfDot+1).equals("actor")){
					parser.read(new File(dataDirectory,dataFileName),result);
				}
			}
		}
		return result;
	}
	private void read(File dataFile, List<Actor> actors){
		Actor result = new Actor();
		actors.add(result);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(dataFile));
			for(String variableName: variableNames){
				if(setMethods.containsKey(variableName)){
					setMethods.get(variableName).invoke(result, returnTypes.get(variableName).read(reader));
				}
			}
			reader.close();
			reader = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if(reader != null ){
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void write(String dataDirectoryName, List<Actor> actors){
		DataParser.write(new File(dataDirectoryName),actors);
	}
	public static void write(File dataDirectory,List<Actor> actors){
		DataParser parser = new DataParser();
		BufferedWriter writer = null;
		for(Actor actor: actors){
			try {
				writer = new BufferedWriter(new FileWriter(new File(dataDirectory,actor.getId()+".actor")));
				parser.write(writer, actor);
				writer.close();
				writer = null;
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		if(writer != null ){
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void write(BufferedWriter writer, Actor actor) throws IOException{
		try{
			for(String variableName: variableNames){
				returnTypes.get(variableName).write(writer,getMethods.get(variableName).invoke(actor));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
