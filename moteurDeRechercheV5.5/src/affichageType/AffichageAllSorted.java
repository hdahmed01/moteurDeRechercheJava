package affichageType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import score.Stat3;

public class AffichageAllSorted implements Affichagetype {

	@Override
	public List<String> affiche1(List<Stat3> l, List<String> requete) {
		
		List<String> list=new ArrayList<>();
		Collections.sort(l);
		if(l.size()>=10) {
			for(int i=0;i<=10;i++) {
				list.add(l.get(i).getPath());
			}
		}
		else {
			for(int i=0;i<l.size();i++) {
				list.add(l.get(i).getPath());
			}
		}
			
		
		
		return list;
	}

	
	
	
}
