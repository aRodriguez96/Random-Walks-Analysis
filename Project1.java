import java.util.concurrent.ThreadLocalRandom;
import java.util.HashSet;
import java.io.*;

public class Project1 {
	public final class global {
	    static final int max_steps = 40;
	    static final int N_W = 100000; 
	    static final int N_T = 930; 
	}
	
	public static void main(String[] args) throws IOException {
		long startTime;
		double totDist;
		double suc;
        int tnum;
        int index;
        long stopTime;
        long elapsedTime;
		for(int dim = 2; dim <= 4; dim++) {
			System.out.println(dim+" DIMENSIONS:");
			System.out.println();
			startTime = System.currentTimeMillis();
			totDist = 0.0;
			suc = 0.0;
	        tnum=0;
	        Multithread array[] = new Multithread[global.N_T];
	        for (int steps=10; steps<=global.max_steps; steps++) { 
	        	for(int j = 1; j<=30;j++) { 
	        		array[tnum] = new Multithread(steps, global.N_W, dim); 
	        		array[tnum].start();
	        		tnum++;
	        	}
	        } 
	        try {
	        	for (int i=0; i < global.N_T; i++) {
	        		array[i].join(); 
	        	}
	        } catch(Exception e) {}
	        index = 0;
	        for(int st = 10; st<=40; st++) {
	        	for(int t = 1; t<=30;t++) { 
	        		totDist += array[index].dist;
	        		suc += array[index].success;
	        		index++;
	        	}
				System.out.println("n= "+st+" Rn= "+totDist/suc+" fSAW= "+suc/(global.N_T * global.N_W));
				totDist = 0;
				suc=0;
	        }
	        stopTime = System.currentTimeMillis();
	        elapsedTime = stopTime - startTime;
	        System.out.println("\nRun Time in seconds: "+elapsedTime/1000.0);
	        System.out.println();
	        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	        System.out.println();
		}//dimensions
	}//main
}//class

class Multithread extends Thread { 
	public int n=0;
	int success = 0;
	int dist = 0;
	
	Multithread(int steps, int N_W, int dimension){
		if(dimension == 2) {
			runWalks2D(steps, N_W); 
		}
		else if (dimension == 3) {
			runWalks3D(steps, N_W); 
		}
		else if (dimension == 4) {
			runWalks4D(steps, N_W); 
		}
	}	
	
	public void runWalks2D(int numSteps, int N_W) {
		n = numSteps;
		int px;
		int py;
		int cx;
		int cy;
		int nx;
		int ny;
		int stepsTaken;
		String prev; 
		String next;  
		String current;
		HashSet<String> hash = new HashSet<String>(); 
		for(int walk = 1; walk <= N_W; walk++) {
			px=0;
			py=0;
			cx=0;
			cy=0;
			nx=0;
			ny=0;
			stepsTaken=1;
			prev = px+","+py;
			current = cx+","+cy;
			next = nx+","+ny; 
			hash.clear();
			hash.add(current);
			int rand = ThreadLocalRandom.current().nextInt(4);
			if(rand==0) {
				ny++;
			}else if(rand==1) {
				ny--;
			}else if(rand==2) {
				nx--;
			}else if(rand==3) {
				nx++;
			}
			next = nx+","+ny;
			hash.add(next);
			prev=current;
			current = next;
			cx=nx;
			cy=ny;
			for (int step = 2; step <= numSteps; step++) {
				do {
					nx=cx;
					ny=cy;
					rand = ThreadLocalRandom.current().nextInt(4);
					if(rand==0) {
						ny++;
					}else if(rand==1) {
						ny--;
					}else if(rand==2) {
						nx--;
					}else if(rand==3) {
						nx++;
					}
					next = nx+","+ny;
				}while(next.contentEquals(prev));
				if(hash.contains(next)) {
					break;
				}else {
					hash.add(next);
					stepsTaken++;
					prev=current;
					current = next;
					cx=nx;
					cy=ny;
				}
			}//loop for steps
			if(stepsTaken==numSteps) {
				success++;
				dist += ((cx)*(cx))+((cy)*(cy));
			}
		}//loop for walk
	}//runWalks2D
	public void runWalks3D(int numSteps, int N_W) {
		n = numSteps;
		int px;
		int py;
		int pz;
		int cx;
		int cy;
		int cz;
		int nx;
		int ny;
		int nz;
		int stepsTaken;
		String prev; 
		String current;
		String next; 
		HashSet<String> hash = new HashSet<String>(); 
		for(int walk = 1; walk <= N_W; walk++) {
			px=0;
			py=0;
			pz=0;
			cx=0;
			cy=0;
			cz=0;
			nx=0;
			ny=0;
			nz=0;
			stepsTaken=1;
			prev = px+","+py+","+pz;
			current = cx+","+cy+","+cz;
			next = nx+","+ny+","+nz; 
			hash.clear();
			hash.add(current);
			int rand = ThreadLocalRandom.current().nextInt(6);
			if(rand==0) {
				nx++;
			}else if(rand==1) {
				nx--;
			}else if(rand==2) {
				ny++;
			}else if(rand==3) {
				ny--;
			}else if(rand==4) {
				nz++;
			}else if(rand==5) {
				nz--;
			}
			next = nx+","+ny+","+nz; next = nx+","+ny;
			hash.add(next);
			prev=current;
			current = next;
			cx=nx;
			cy=ny;
			cz=nz;
			for (int step = 2; step <= numSteps; step++) {
				do {
					nx=cx;
					ny=cy;
					nz=cz;
					rand = ThreadLocalRandom.current().nextInt(4);
					if(rand==0) {
						nx++;
					}else if(rand==1) {
						nx--;
					}else if(rand==2) {
						ny++;
					}else if(rand==3) {
						ny--;
					}else if(rand==4) {
						nz++;
					}else if(rand==5) {
						nz--;
					}
					next = nx+","+ny+","+nz; next = nx+","+ny;
				}while(next.contentEquals(prev));
				if(hash.contains(next)) {
					break;
				}else {
					hash.add(next);
					stepsTaken++;
					prev=current;
					current = next;
					cx=nx;
					cy=ny;
					cz=nz;
				}
			}//loop for steps
			if(stepsTaken==numSteps) {
				success++;
				dist += (cx*cx)+(cy*cy)+(cz*cz);
			}
		}//loop for walk
	}//runWalks3D
	public void runWalks4D(int numSteps, int N_W) {
		n = numSteps;
		int px;
		int py;
		int pz;
		int pq;
		int cx;
		int cy;
		int cz;
		int cq;
		int nx;
		int ny;
		int nz;
		int nq;
		int stepsTaken;
		String prev; 
		String current;
		String next;  
		HashSet<String> hash = new HashSet<String>(); 
		for(int walk = 1; walk <= N_W; walk++) {
			px=0;
			py=0;
			pz=0;
			pq=0;
			cx=0;
			cy=0;
			cz=0;
			cq=0;
			nx=0;
			ny=0;
			nz=0;
			nq=0;
			stepsTaken=1;
			prev = px+","+py+","+pz+","+pq;
			current = cx+","+cy+","+cz+","+cq;
			next = nx+","+ny+","+nz+","+nq; 
			hash.clear();
			hash.add(current);
			int rand = ThreadLocalRandom.current().nextInt(8);
			if(rand==0) {
				nx++;
			}else if(rand==1) {
				nx--;
			}else if(rand==2) {
				ny++;
			}else if(rand==3) {
				ny--;
			}else if(rand==4) {
				nz++;
			}else if(rand==5) {
				nz--;
			}else if(rand==6) {
				nq++;
			}else if(rand==7) {
				nq--;
			}
			next = nx+","+ny+","+nz+","+nq; 
			hash.add(next);
			prev=current;
			current = next;
			cx=nx;
			cy=ny;
			cz=nz;
			cq=nq;
			for (int step = 2; step <= numSteps; step++) {
				do {
					nx=cx;
					ny=cy;
					nz=cz;
					nq=cq;
					rand = ThreadLocalRandom.current().nextInt(8);
					if(rand==0) {
						nx++;
					}else if(rand==1) {
						nx--;
					}else if(rand==2) {
						ny++;
					}else if(rand==3) {
						ny--;
					}else if(rand==4) {
						nz++;
					}else if(rand==5) {
						nz--;
					}else if(rand==6) {
						nq++;
					}else if(rand==7) {
						nq--;
					}
					next = nx+","+ny+","+nz+","+nq; 
				}while(next.contentEquals(prev));
				if(hash.contains(next)) {
					break;
				}else {
					hash.add(next);
					stepsTaken++;
					prev=current;
					current = next;
					cx=nx;
					cy=ny;
					cz=nz;
					cq=nq;
				}
				
			}//loop for steps
			if(stepsTaken==numSteps) {
				success++;
				dist += (cx*cx)+(cy*cy)+(cz*cz)+(cq*cq);
			}
		}//loop for walk
	}//runWalks4D
} //class


