package id.ac.its.squealer.entity;

import java.io.*;

public class HighScore {
	private StringBuilder fileName;

	public HighScore(int level) {
		fileName = new StringBuilder();
		fileName.append("Resource/Highscore/Level" + level + ".dat");
		if(!new File(fileName.toString()).exists())
			storeFileInit(fileName.toString());
	}
	
	private void storeFileInit(String filename) {
		try {
			ObjectOutputStream outfile = new ObjectOutputStream(new FileOutputStream(filename));
			outfile.write(-100);
			outfile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getHighScore() {
		try {
			ObjectInputStream infile = new ObjectInputStream(new FileInputStream(fileName.toString()));
			int score = infile.read();
			infile.close();
			return score;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return -100;
	}
	
	public void highScoreSet(int score) {
		try {
			ObjectOutputStream outfile = new ObjectOutputStream(new FileOutputStream(fileName.toString()));
			outfile.write(score);
			outfile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
