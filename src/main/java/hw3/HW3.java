package hw3;

/**
 * @Description: TODO
 * @author: Wei Liang
 * @date: 2/26/2023 12:15 AM
 */
public class HW3 {
    public static void main(String[] args) {
        int[] sampleRTT = {220, 220, 240};
        double estRTT = 280;
        double devRTT = 27;
        double timeInterval=0;
        int j= (sampleRTT.length);
        int i=0;
        while (i<j) {
            estRTT = (1-0.125)*estRTT + 0.125*sampleRTT[i];
            devRTT = 0.75*devRTT + 0.25*Math.abs(estRTT-sampleRTT[i]);
            timeInterval = estRTT + devRTT*4;
            System.out.println("estRTT: "+estRTT+", devRTT: "+devRTT+", timeInterval: "+timeInterval);
            i++;
        }
    }
}
