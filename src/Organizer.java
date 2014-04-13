import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.util.ArrayList;

import entagged.audioformats.AudioFile;
import entagged.audioformats.AudioFileIO;
import entagged.audioformats.Tag;
import entagged.audioformats.exceptions.CannotReadException;


public class Organizer {

	static String prefisso;

	public static void main(String[] args) throws IOException{
		if(args.length == 1)
			prefisso = args[0];
		else
			//prefisso = "./";
			System.exit(0);

		if (prefisso.charAt(prefisso.length() - 1) != '/')
			prefisso = prefisso + '/';

		ordinaCartella(prefisso);
	}
	
	public static void ordinaFile(String path, String nomefile) throws IOException{
		try {
			File file = new File(path+nomefile);
			AudioFile mp3 = AudioFileIO.read(file);

			Tag id3tag = mp3.getTag();
			String artist = id3tag.getFirstArtist();
			String album = id3tag.getFirstAlbum();
			String title = id3tag.getFirstTitle();
			
			ArrayList<String> partiNomeFile = split(nomefile, ".");
			String suffisso = "."+partiNomeFile.get(partiNomeFile.size()-1);
			
			Path origine = Paths.get(file.getPath());
			Path cartella = Paths.get(prefisso+artist+"/"+album);
			Path destinazione = Paths.get(prefisso+artist+"/"+album+"/"+artist+" ("+album+") "+title+suffisso);
			
			//System.out.println(artist+" ("+album+") "+title+suffisso);
			//System.out.println("cartella:     " + cartella.toString());
			System.out.println("origine:      " + origine.toString());
			//System.out.println("destinazione: " + destinazione.toString());
			
			Files.createDirectories(cartella);
			Files.move(origine, destinazione, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (CannotReadException e) {
			System.out.println(path+nomefile+" File non riconosciuto ############");
			//System.out.println(file.getPath());
			Files.createDirectories(Paths.get(prefisso+"spostati/"));
			Path origine = Paths.get(path+nomefile);
			Path destinazione = Paths.get( prefisso+"spostati/"+nomefile);
			
			//System.out.println(origine.toString());
			//System.out.println(destinazione.toString());
			//Files.createFile(destinazione);
			Files.move(origine, destinazione, StandardCopyOption.REPLACE_EXISTING);	
		}
	}

	public static void ordinaCartella(String path) throws IOException{

		File directory = new File(path);
		String lista[] = directory.list();

		for (int i = 0; i < lista.length; i++) {
		
			if (lista[i].charAt(0) == '.') {
				File cartellaDaRimuovere = new File(path+lista[i]+"/");
				if(!Files.isDirectory(Paths.get(cartellaDaRimuovere.getPath())))
					Files.delete(Paths.get(path+lista[i]));
				else {
					String fileDaRimuovere[] = cartellaDaRimuovere.list();
					System.out.println("DA ELIMINARE "+cartellaDaRimuovere.getPath().toString());
					for(int j = 0; j < fileDaRimuovere.length; j++)
						Files.delete(Paths.get(path+lista[i]+"/"+fileDaRimuovere[j]));
					Files.delete(Paths.get(path+lista[i]));
				}
				continue;
			}

			File file = new File(path + lista[i]);
			
			if (file.isDirectory()) {
				//System.out.println(path+lista[i]+"/");
				ordinaCartella(path+lista[i]+"/");
				continue;
			}

			ordinaFile(path, lista[i]);
		}
		
		if((directory.list()).length == 0) {
			Files.delete(Paths.get(path));
		}
	}
	
	public static ArrayList<String> split(String stringa, String carattere){
		ArrayList<String> split = new ArrayList<String>();
		if(stringa.length()==0)
			return split;
		if(carattere.length()==0){
			split.add(stringa);
			return split;
		}
		String temp = "";
		boolean controlloOk = false;
		for(int i=0; i<stringa.length(); i++){
			if(stringa.substring(i, i+1).equals(carattere.substring(0, 1))){
				controlloOk = true;
				int j = 0;
				while(j<carattere.length()-1 && controlloOk){
					j++;
					if(!stringa.substring(i+j,i+j+1).equals(carattere.substring(j, j+1)))
						controlloOk = false;
				}
				if(controlloOk){
					split.add(temp);
					i += j;
					temp = "";
				}
				else
					temp += stringa.substring(i,i+1);
			}
			else
				temp += stringa.substring(i,i+1);
		}
		if(!temp.equals("")){
			split.add(temp);
			temp = "";
		}
		return split;
	}
}
