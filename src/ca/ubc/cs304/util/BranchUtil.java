package ca.ubc.cs304.util;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Branch;
import java.util.ArrayList;


public class BranchUtil {
    private static DatabaseConnectionHandler dbHandler =  DatabaseConnectionHandler.getInstance();
    private static BranchUtil branchUtil;
    private static String[] allBranches;

    private BranchUtil() {
        allBranches = branchesToStringArray();
    }

    public static BranchUtil getInstance() {
        if (branchUtil == null) {
            branchUtil = new BranchUtil();
        }
        return branchUtil;
    }

    private ArrayList<Branch> getAllBranches(){
        return dbHandler.getAllBranches();
    }

    private String[] branchesToStringArray(){
        ArrayList<Branch> branches = this.getAllBranches();
        String[] branchStr = new String[branches.size()];
        int i = 0;
        for (Branch b : branches) {
            branchStr[i++] = b.toString();
        }
        return branchStr;
    }

    public String[] getAllBranchesAsStringArray(){
        return allBranches;
    }

    public String getDefaultBranchAsString(){
        return allBranches[0];
    }

    public static Branch decodeBranchFromString(String branch){
        if (branch == null) {
            return null;
        }
        String[] values = branch.split(",");
        return new Branch(values[0], values[1]);
    }
}
