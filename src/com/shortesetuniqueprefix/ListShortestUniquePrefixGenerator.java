package com.shortesetuniqueprefix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.config.Config;
import com.util.*;

/**
 * The main class for generating LSUP, you can override some methods for customization.
 * 
 * @author shenchen
 *
 */

public class ListShortestUniquePrefixGenerator<K> {
	private final Map<Integer, List<K>> indexToOutputLSUPMap = new HashMap<Integer, List<K>>();
	
	//private final Logger logger = Logger.getLogger(this.getClass());
			
	// This method will fulfill indexToSUPMap and indexToSUPMap
	public final Map<Integer, List<K>> generate(final Map<Integer, List<K>> indexToInputSequenceMap) throws Exception {
		indexToOutputLSUPMap.clear();
		doGenerate(indexToInputSequenceMap);
		return indexToOutputLSUPMap;
	}
	
	/*
	 * */
	private void doGenerate(final Map<Integer, List<K>> indexToInputSequenceMap) {
		try {
			// create workplace in WORK_PLACE_PATH
			initWorkPlace();
			
			final int totalCountOfSequences = writeToInitFile(indexToInputSequenceMap);
			
			final String initFilePath = Config.getAttri("WORK_PLACE_PATH") + "\\c" + Integer.valueOf(totalCountOfSequences);
			
			doGenerateInFileRecursion(initFilePath, Integer.valueOf(Config.getAttri("INIT_RECURSION_LEVEL")), indexToInputSequenceMap);
		} catch (IOException ex) {
			
		} catch (Exception ex) {
			
		}
	}
	
	/*
	 * */
	private void initWorkPlace() throws IOException {
		final String workPlacePath = Config.getAttri("WORK_PLACE_PATH");
		
		File workPlaceDirFile = new File(workPlacePath);
		
		if (workPlaceDirFile.exists()) {
			Util.delFileOrFolder(workPlaceDirFile.getAbsolutePath());
			//logger.info("Delete existent work place.");
		}
		workPlaceDirFile.mkdirs();
		//logger.info("Create work place in \"" + workPlacePath + "\".");
	}
	
	/*
	 * */
	private int writeToInitFile(final Map<Integer, List<K>> indexToInputSequenceMap) throws IOException {
		final String tempInitFilePath = Config.getAttri("WORK_PLACE_PATH") + "\\temp";
		final FileWriter tempInitFileWriter = new FileWriter(tempInitFilePath);
		
		Integer totalCountOfSequences = 0;
		
		//logger.info("Start writing initial file.");
		for (Map.Entry<Integer, List<K>> entry : indexToInputSequenceMap.entrySet()) {
			Integer index = entry.getKey();
			List<K> sequence = entry.getValue();
			
			if (sequence == null || sequence.isEmpty()) {
				//logger.debug("Sequence " + index + " is null or empty.");
				continue;
			}
			
			String sequenceStr = sequence.toString();
			tempInitFileWriter.write(index + ":" + sequenceStr.substring(1, sequenceStr.length()-1) + "\n");
			totalCountOfSequences++;
		}
		//tempInitFileWriter.flush();
		tempInitFileWriter.close();
		
		final String initFilePath = Config.getAttri("WORK_PLACE_PATH") + "\\c" + totalCountOfSequences;
		Util.copyFile(tempInitFilePath, initFilePath);	
		Util.delFileOrFolder(tempInitFilePath);
		
		//logger.info("Finish writing.");
		return totalCountOfSequences;
	}
	
	/*
	 *  Use "c + number of sequences" as the name of file
	 *  
	 *  ALGORITHM:
	 *  1) Base case:
	 *     	Step 1: If the path is too long or the number of sequences is less than MAX_COUNT_OF_SEQUENCES_IN_SINGLE_FILE, 
	 *  	stop dividing and try to generate LSUP in memory
	 *  2) Recursive case:
	 *  	Step 1: Find parent path
	 *     	Step 2: Create sub folders(with single file) according number at recursionLevel position
	 *     	Step 3: Rename all single files and call doDivide respectively
	 * */
	private boolean doGenerateInFileRecursion(String absSourceForDividePath, int recursionLevel, 
			final Map<Integer, List<K>> indexToInputSequenceMap) throws Exception {
		File sourceFile = new File(absSourceForDividePath);
		Integer sequenceCount = new Integer(sourceFile.getName().substring(1));
		
		// For windows, file path has a max length(255), you can configure this in config.properties
		int filePathLengthLimit = Integer.parseInt(Config.getAttri("FILE_PATH_LENGTH_LIMIT"));
		int singleFileItemMaxCount = Integer.parseInt(Config.getAttri("MAX_COUNT_OF_SEQUENCES_IN_SINGLE_FILE"));
		
		// Base case: 
		if (sourceFile.getAbsolutePath().length() >= filePathLengthLimit|| sequenceCount <= singleFileItemMaxCount) {
			return doGenerateInMemory(sourceFile, recursionLevel, indexToInputSequenceMap);
		}
		  
		// K.toString() to FileWriter object Map
		Map<String, FileWriter> fileWriterObjMap = new HashMap<String, FileWriter>();
		
		// a counterpart Map of fileWriterObjMap
		Map<FileWriter, String> kStringMap = new HashMap<FileWriter, String>();
		
		// File object Map
		Map<FileWriter, File> fileObjMap = new HashMap<FileWriter, File>();
		
		// Count of sequences Map
		Map<FileWriter, Integer> sequenceCountMap = new HashMap<FileWriter, Integer>();
		
		// Recursive case:
		
		// Step 1
		String absParentPath = sourceFile.getParent() + "\\";
		
		// Step 2
		BufferedReader sourceFileBufferedReader = new BufferedReader(new FileReader(sourceFile));
		String formattedSequence = null;
		while((formattedSequence = sourceFileBufferedReader.readLine()) != null) {
			String sequence = Util.getSequenceFromFormattedSequence(formattedSequence);
			String[] sequenceArray = sequence.split(", ");
			if (recursionLevel <= sequenceArray.length - 1) {
				File targetFolder = new File(absParentPath + sequenceArray[recursionLevel]);
				if (!targetFolder.exists() || !targetFolder.isDirectory()) {
					targetFolder.mkdirs();
					File targetFile = new File(absParentPath + sequenceArray[recursionLevel] + "\\temp");
					targetFile.createNewFile();
					FileWriter targetFileWriter = new FileWriter(targetFile);
					fileWriterObjMap.put(sequenceArray[recursionLevel], targetFileWriter);
					kStringMap.put(targetFileWriter, sequenceArray[recursionLevel]);
					fileObjMap.put(targetFileWriter, targetFile);
					sequenceCountMap.put(targetFileWriter, 0);
				}
				
				FileWriter targetFileWriter = fileWriterObjMap.get(sequenceArray[recursionLevel]);	
				targetFileWriter.write(formattedSequence + "\n");
				sequenceCountMap.put(targetFileWriter, sequenceCountMap.get(targetFileWriter) + 1);
			}
		}
		sourceFileBufferedReader.close();
		sourceFile.delete();
		
		// Step 3
		for (FileWriter fw : fileWriterObjMap.values()) {
			fw.close();
			String renameFileName = fileObjMap.get(fw).getParent() + "\\c" + sequenceCountMap.get(fw);
			File renameFile = new File(renameFileName);
			fileObjMap.get(fw).renameTo(renameFile);
			boolean isSuccess = doGenerateInFileRecursion(renameFile.getAbsolutePath(), recursionLevel + 1, 
					indexToInputSequenceMap);
			if (!isSuccess) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * recursionLevel represents the length of common prefix currently
	 * */
	private boolean doGenerateInMemory(File sourceFile, int recursionLevel, 
			final Map<Integer, List<K>> indexToInputSequenceMap) throws Exception {
		Map<Integer, List<String>> indexToSequenceStrListMap = new HashMap<Integer, List<String>>();
		
		BufferedReader sourceFileBufferedReader = new BufferedReader(new FileReader(sourceFile));
		String formattedSequence = null;
		while((formattedSequence = sourceFileBufferedReader.readLine()) != null) {
			String sequence = Util.getSequenceFromFormattedSequence(formattedSequence);
			String[] sequenceArray = sequence.split(", ");
			List<String> sequenceList = new ArrayList<String>();
			Collections.addAll(sequenceList, sequenceArray);
			String index = Util.getIndexFromFormattedSequence(formattedSequence);
			indexToSequenceStrListMap.put(Integer.valueOf(index), sequenceList);
		}
		sourceFileBufferedReader.close();
		
		// generate initial node
		Set<Integer> indexList = indexToSequenceStrListMap.keySet();
		Node initNode = new Node(indexList, recursionLevel, null); 
		
		doGenerateInMemoryRecursion(initNode, indexToSequenceStrListMap, indexToInputSequenceMap);
		return true;
	}
	
	/*
	 *  Use "c + number of sequences" as the name of file
	 *  
	 *  ALGORITHM:
	 *  1) Base case:
	 *     	Step 1: Only one index in the set, fulfill the indexToOutputLSUPMap
	 *  2) Recursive case:
	 *  	Step 1: Divide sequence into different node and call doGenerateInMemoryRecursion respectively
	 * */
	@SuppressWarnings("unchecked")
	private boolean doGenerateInMemoryRecursion(Node node, Map<Integer, List<String>> indexToSequenceStrListMap, 
			final Map<Integer, List<K>> indexToInputSequenceMap) {
		// Base case
		if (node.indexSet.size() == 1) {
			List<K> LSUPList = new ArrayList<K>();
			for (Integer index : node.indexSet) {
				for (int i = 0; i <= node.level - 1; i++) {
					LSUPList.add((K) indexToInputSequenceMap.get(index).get(i));
				}
				indexToOutputLSUPMap.put(index, LSUPList);
			}
			return true;
		}
		
		// Recursion case:
		Map<String, Node> nodeMap = new HashMap<String, Node>();
		for (Integer index : node.indexSet) {
			List<String> progressionList = indexToSequenceStrListMap.get(index);
			
			// if progression is not enough long, use special symbol for marking it and continue
			if (node.level > progressionList.size() - 1) {
				indexToOutputLSUPMap.put(index, null);
				continue;
			}
			
			String sequenceItemStr = indexToSequenceStrListMap.get(index).get(node.level);
			Node targetNode = nodeMap.get(sequenceItemStr);
			if (targetNode == null) {
				Set<Integer> indexSet = new HashSet<Integer>();
				indexSet.add(index);
				Node newNode = new Node(indexSet, node.level + 1, sequenceItemStr);
				nodeMap.put(sequenceItemStr, newNode);
			} else {
				targetNode.indexSet.add(index);
			}
		}
		
		for (Node n : nodeMap.values()) {
			boolean isSuccess = doGenerateInMemoryRecursion(n, indexToSequenceStrListMap, 
					indexToInputSequenceMap);
			if (!isSuccess) {
				return false;
			}
		}
		return true;
	}
}