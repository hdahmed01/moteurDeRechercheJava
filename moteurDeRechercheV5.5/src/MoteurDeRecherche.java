import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

//import java.util.Collections;
import java.util.List;

import affichageType.AffichageAllSorted;
import affichageType.Affichagetype;
import affichageType.AfficheBestOne;
import analyseur.Analyseur;
import analyseur.Occurence;
//import analyseur.MapOccurenceAnalyseur;
//import analyseur.Stat1;
import document.Document;

import indexeur.Index;
import indexeur.Index1;
import indexeur.MapIndexeur;
import lecture.LecteurDocument;
import lecture.LecteurMotParMot;


import pretraiteur.Pretraiteur;

import score.HavingAll;

import score.Score;
import score.Sommeocc;
//import score.Stat3;
import score.Stat2;

public class MoteurDeRecherche {
    // attributs
    private Affichagetype affichagetype;
    private LecteurDocument lecteur;
    private Analyseur analyseur ;
    private Score score;
    private Index indexeur;
    private List<Document> document=new ArrayList<>();
    private List<Pretraiteur> pretraiteurs;
    // constructeurs
    public MoteurDeRecherche(){
        this.lecteur=new LecteurMotParMot();
        this.analyseur=new Occurence();
        this.indexeur=new MapIndexeur();
        this.document=new ArrayList<>();
        this.score=new Sommeocc(); 
        this.pretraiteurs=new ArrayList<>();
        this.affichagetype=new AffichageAllSorted();
    }
    public   MoteurDeRecherche(Analyseur analyseur,Index indexeur,List<Pretraiteur> pretraiteurs,LecteurDocument lecteur,Score score){
        this.lecteur=lecteur;
        this.analyseur=analyseur;
        this.indexeur=indexeur;
        this.score=score;
        this.pretraiteurs=pretraiteurs;
        this.affichagetype=new AfficheBestOne();
    }
  //setters
    public  void setPretraiteurs(List<Pretraiteur>pretraiteurs){
        this.pretraiteurs=pretraiteurs;
    }
    public void setAnalyseur(Analyseur analyseur){
        this.analyseur=analyseur;
    }
    public void setScore(Score score){
        this.score =score;

    }
    public void setIndex(Index index){
        this.indexeur=index;
    }
    public void setAffichageType(Affichagetype affichagetype) {
        this.affichagetype=affichagetype;
    }
    //getters
    public  List<Pretraiteur> getPretraiteurs(){
       return  this.pretraiteurs;
    }
    public  Analyseur getAnalyseur(){
        return  this.analyseur;
        
     }
    public Score getScore(){
    	return this.score ;

    }
    public Index getIndex(){
    	return this.indexeur;
    }
    public Affichagetype getAffichageType() {
    	return this.affichagetype;
    }
    public List<Document> getDocument() {
    	return this.document;
    }
    //dev
    private void indexSingleDocument(String path){
        //on peut ajouter un autre parametre entier pour determiner quelle procedure d'indexation on adapte
        List<String> text=lecteur.readDocument(path);
        Document doc=new Document(path,text);
        if(!document.contains(doc)){
            document.add(doc);
        }
        for(Pretraiteur i: pretraiteurs) {
        	
        	i.traiter(text);
        	
        }
        indexeur.addIndex(analyseur.analyser(text), path);

        
       

    }
    private void indexDirectory(String path){
        File pathFile = Paths.get(path).toFile();
        File[] files = pathFile.listFiles();
        for(File f: files ){
            if(f.isDirectory()){
                indexDirectory(f.getAbsolutePath());
            }else{
                indexSingleDocument(f.getAbsolutePath());
            }
        }
    }
    public void index(String path){
        File filePath = Paths.get(path).toFile();
        if(filePath.exists()){
            if(filePath.isDirectory()){
                //System.out.println(path+" is a directory");
                indexDirectory(path);
            }else{
               // System.out.println(path+" is a file");
                indexSingleDocument(path);
            }
        }else{
            System.out.println(path+" does not exist");
        }

    }
    public List<String> traitementDeRequete(String requete){
    	String[] requeteMotsparMOts =requete.split(" ");
    	List<String> list0=Arrays.asList(requeteMotsparMOts);
    	for(Pretraiteur i: pretraiteurs) {
	         	i.traiter(list0);
    	
    	}return list0;
    }
    public List<List<Stat2>> rechercherParRequete(List<String> requete){
    	

         	         	
       	 return indexeur.getStatrequette(requete);
    	
    	
    }


    

}
