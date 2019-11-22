package ca.ubc.cs304.util;

import ca.ubc.cs304.model.Branch;


public class BranchUtil {
    public static final Branch VANCOUVER_A = new Branch("Location A", "Vancouver");
    public static final Branch VANCOUVER_B = new Branch("Location B", "Vancouver");
    public static final Branch RICHMOND_A = new Branch("Location A", "Richmond");
    public static final Branch RICHMOND_B = new Branch("Location B", "Richmond");
    public static final Branch[] allBranches = {VANCOUVER_A, VANCOUVER_B, RICHMOND_A, RICHMOND_B};

    public static String[] branchesToStringArray(){
        String[] branchesArray = {BranchUtil.VANCOUVER_A.toString(),
                BranchUtil.VANCOUVER_B.toString(),
                BranchUtil.RICHMOND_A.toString(),
                BranchUtil.RICHMOND_B.toString()};
        return branchesArray;
    }

    public static Branch decodeBranchFromString(String branch){
        String[] values = branch.split(",");
        return new Branch(values[0], values[1]);
    }
}
