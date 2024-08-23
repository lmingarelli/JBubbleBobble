package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Player;

public class PlayersRecord {
	private static String fileName = "players.xml";
	private static String filePath = "assets/data/" + fileName;

	// Return false if the file already exists, return true if this method has
	// created it. The file will be created with current player info
	public boolean initXMLFile(Player player) {
		File file = new File(filePath);
		boolean isFileCreated = false;

		try{
			if(file.createNewFile()){
				// Create a DocumentBuilder
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();

				// Create a new Document
				Document document = builder.newDocument();

				// Creating the player xml section
				Element playerEl = getPlayerElement(player, document);

				// Create root element
				Element root = document.createElement("players");
				root.appendChild(playerEl);
				document.appendChild(root);

				// Write to XML file
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
//				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//				transformer.setOutputProperty(
//						"{http://xml.apache.org/xslt}indent-amount", "2");
				DOMSource source = new DOMSource(document);

				StreamResult result = new StreamResult(filePath);
				transformer.transform(source, result);

				isFileCreated = true;
				System.out.println("XML file created successfully!");
			}
		}catch (ParserConfigurationException e){
			System.out.println("PlayersRecords.initXMLFile error: " + e.getCause());
			e.printStackTrace();
		}catch (TransformerConfigurationException e){
			System.out.println("PlayersRecords.initXMLFile error: " + e.getCause());
			e.printStackTrace();
		}catch (TransformerException e){
			System.out.println("PlayersRecords.initXMLFile error: " + e.getCause());
			e.printStackTrace();
		}catch (DOMException e){
			System.out.println("PlayersRecords.initXMLFile error: " + e.getCause());
			e.printStackTrace();
		}catch (IOException e){
			System.out.println("PlayersRecords.initXMLFile error: " + e.getCause());
			e.printStackTrace();
		}catch (TransformerFactoryConfigurationError e){
			System.out.println("PlayersRecords.initXMLFile error: " + e.getCause());
			e.printStackTrace();
		}

		return isFileCreated;
	}

	// Return true if the player is in the record file. If it finds the player it
	// will also load the info from the file to the player object.
	public boolean isPlayerInRecord(Player player) {
		try{
			String playerNickname = player.getNickname();

			// Specify the file path as a File object
			File xmlFile = new File(filePath);

			// Create a DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Parse the XML file
			Document document = builder.parse(xmlFile);

			// Access elements by tag name
			NodeList nodeList = document.getElementsByTagName("player");
			for(int i = 0; i < nodeList.getLength(); i++){
				Node currentNode = nodeList.item(i);
				Element currentNodeAsElement = (Element) currentNode;
				String currentNickname = currentNodeAsElement.getAttribute("nickname");

				if(currentNickname.equals(playerNickname)){
					player.setWinNumber(
							retrieveInfoFromPlayerTag("win", currentNodeAsElement));

					player.setLoseNumber(
							retrieveInfoFromPlayerTag("lose", currentNodeAsElement));

					player.setBestScore(
							retrieveInfoFromPlayerTag("bestScore", currentNodeAsElement));

					player.setGamesNumber(
							retrieveInfoFromPlayerTag("gamesPlayed", currentNodeAsElement));

					return true;
				}
			}
		}catch (ParserConfigurationException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}catch (SAXException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}catch (IOException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}

		return false;
	}

	public void registerPlayer(Player player) {
		try{
			// Specify the file path as a File object
			File xmlFile = new File(filePath);

			// Create a DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Parse the XML file
			Document document = builder.parse(xmlFile);

			// Access root element
			Element rootElement = document.getDocumentElement();

			// Creating the player xml section
			Element playerEl = getPlayerElement(player, document);

			// Appending new player to root
			rootElement.appendChild(playerEl);

			// Write to XML file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.setOutputProperty(
//					"{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(document);

			StreamResult result = new StreamResult(filePath);
			transformer.transform(source, result);

		}catch (ParserConfigurationException e){
			System.out
					.println("PlayersRecords.registerPlayer error: " + e.getCause());
			e.printStackTrace();
		}catch (SAXException e){
			System.out
					.println("PlayersRecords.registerPlayer error: " + e.getCause());
			e.printStackTrace();
		}catch (IOException e){
			System.out
					.println("PlayersRecords.registerPlayer error: " + e.getCause());
			e.printStackTrace();
		}catch (TransformerConfigurationException e){
			System.out
					.println("PlayersRecords.registerPlayer error: " + e.getCause());
			e.printStackTrace();
		}catch (TransformerException e){
			System.out
					.println("PlayersRecords.registerPlayer error: " + e.getCause());
			e.printStackTrace();
		}
	}

	// Update the player info on the file from the player object. No check on
	// player existing on the file is needed, because it's already done when using
	// isPlayerInRecord()
	public void updatePlayer(Player player) {
		try{
			String playerNickname = player.getNickname();

			// Specify the file path as a File object
			File xmlFile = new File(filePath);

			// Create a DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Parse the XML file
			Document document = builder.parse(xmlFile);

			// Access elements by tag name
			NodeList nodeList = document.getElementsByTagName("player");

			for(int i = 0; i < nodeList.getLength(); i++){
				Node currentNode = nodeList.item(i);
				Element currentNodeAsElement = (Element) currentNode;
				String currentNickname = currentNodeAsElement.getAttribute("nickname");

				if(currentNickname.equals(playerNickname)){
					savePlayerInfo("win", currentNodeAsElement, player.getWinNumber());
					savePlayerInfo("lose", currentNodeAsElement, player.getLoseNumber());
					savePlayerInfo("bestScore", currentNodeAsElement,
							player.getBestScore());
					savePlayerInfo("gamesPlayed", currentNodeAsElement,
							player.getGamesNumber());
				}
			}

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.setOutputProperty(
//					"{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(document);

			StreamResult result = new StreamResult(filePath);
			transformer.transform(source, result);

		}catch (ParserConfigurationException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}catch (SAXException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}catch (IOException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}catch (TransformerException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}
	}

	public ArrayList<Player> getLeaderboard() {
		ArrayList<Player> currentLeaderboard = new ArrayList<Player>();

		try{
			// Specify the file path as a File object
			File xmlFile = new File(filePath);

			// Create a DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Parse the XML file
			Document document = builder.parse(xmlFile);

			// Access elements by tag name
			NodeList nodeList = document.getElementsByTagName("player");
			for(int i = 0; i < nodeList.getLength(); i++){
				// Current node object and nickname
				Node currentNode = nodeList.item(i);
				Element currentNodeAsElement = (Element) currentNode;
				String currentNickname = currentNodeAsElement
						.getAttribute("nickname");

				// Best score
				int currentBestScore = 0;
				NodeList scoreNodeList = currentNodeAsElement
						.getElementsByTagName("bestScore");
				if(scoreNodeList != null && scoreNodeList.getLength() > 0){
					NodeList subList = scoreNodeList.item(0).getChildNodes();

					if(subList != null && subList.getLength() > 0){
						currentBestScore = Integer.parseInt(subList.item(0).getNodeValue());
					}
				}

				Player playerToAdd = new Player();
				playerToAdd.setBestScore(currentBestScore);
				playerToAdd.setNickname(currentNickname);

				// If there are less than 5 player in the leaderboard add it
				if(currentLeaderboard.size() < 5){

					currentLeaderboard.add(playerToAdd);
				}else{
					// Otherwise check if the current best score has to go inside the
					// leaderboard
					if(currentBestScore > currentLeaderboard.get(4).getBestScore()){

						Player playerToMove = null;

						for(int j = 0; j < currentLeaderboard.size(); j++){
							Player currentPlayer = currentLeaderboard.get(j);

							if(playerToMove == null
									&& currentBestScore > currentPlayer.getBestScore()){
								playerToMove = currentPlayer;
								currentLeaderboard.set(j, playerToAdd);
								continue;
							}

							if(playerToMove != null){
								currentLeaderboard.set(j, playerToMove);
								playerToMove = currentPlayer;
							}
						}
					}
				}

				// Sorting the leaderboard by best scores
				currentLeaderboard = new ArrayList<Player>(
						currentLeaderboard.stream()
								.sorted(Comparator.comparing(player -> -player.getBestScore()))
								.collect(Collectors.toList()));
			}
		}catch (ParserConfigurationException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}catch (SAXException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}catch (IOException e){
			System.out
					.println("PlayersRecords.isPlayerInRecord error: " + e.getCause());
			e.printStackTrace();
		}

		return currentLeaderboard;
	}

	private Element getPlayerElement(Player player, Document document) {
		Element playerEl = document.createElement("player");
		playerEl.setAttribute("nickname", player.getNickname());

		Element gamesPlayedEl = document.createElement("gamesPlayed");
		gamesPlayedEl.appendChild(
				document.createTextNode(Integer.toString(player.getGamesNumber())));
		Element winEl = document.createElement("win");
		winEl.appendChild(
				document.createTextNode(Integer.toString(player.getWinNumber())));
		Element loseEl = document.createElement("lose");
		loseEl.appendChild(
				document.createTextNode(Integer.toString(player.getLoseNumber())));
		Element bestScoreEl = document.createElement("bestScore");
		bestScoreEl.appendChild(
				document.createTextNode(Integer.toString(player.getBestScore())));

		playerEl.appendChild(gamesPlayedEl);
		playerEl.appendChild(winEl);
		playerEl.appendChild(loseEl);
		playerEl.appendChild(bestScoreEl);

		return playerEl;
	}

	private int retrieveInfoFromPlayerTag(String childName,
			Element currentNodeAsElement) {

		NodeList nodeList = currentNodeAsElement.getElementsByTagName(childName);
		if(nodeList != null && nodeList.getLength() > 0){
			NodeList subList = nodeList.item(0).getChildNodes();

			if(subList != null && subList.getLength() > 0){
				return Integer.parseInt(subList.item(0).getNodeValue());
			}
		}

		return -1;
	}

	private void savePlayerInfo(String childName,
			Element currentNodeAsElement, int value) {

		NodeList nodeList = currentNodeAsElement.getElementsByTagName(childName);
		if(nodeList != null && nodeList.getLength() > 0){
			NodeList subList = nodeList.item(0).getChildNodes();

			if(subList != null && subList.getLength() > 0){
				subList.item(0).setNodeValue(Integer.toString(value));
			}
		}

	}
}
