package org.app.Models.Helpers;
import java.util.Arrays;
import java.util.Scanner;

/**
 * An algorithm for measuring the difference between two character sequences.
 *
 * <p>
 * This is the number of changes needed to change one sequence into another,
 * where each change is a single character modification (deletion, insertion
 * or substitution).
 * </p>
 *
 * <p>
 * This code has been adapted from Apache Commons Lang 3.3.
 * </p>
 *
 * @since 1.0
 */
public class LevenshteinDistance {

    static Scanner scanner = new Scanner(System.in);

    public static boolean confirmDeletion() {
        System.out.println("Are You Sure You Wanna Delete This Item, Then Enter Yes Otherwise No:");
        String answer = scanner.nextLine();
        if(compute_Levenshtein_distanceDP(answer.toLowerCase(), "Yes") <= 2) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean confirmCancellation() {
        System.out.println("Are You Sure You Wanna Cancel This Reservation, Then Enter Yes Otherwise No:");
        String answer = scanner.nextLine();
        if(compute_Levenshtein_distanceDP(answer.toLowerCase(), "Yes") <= 2) {
            return true;
        } else {
            return false;
        }

    }
    public static int compute_Levenshtein_distanceDP(String str1,
                                              String str2)
    {

        // A 2-D matrix to store previously calculated
        // answers of subproblems in order
        // to obtain the final

        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++)
        {
            for (int j = 0; j <= str2.length(); j++) {

                // If str1 is empty, all characters of
                // str2 are inserted into str1, which is of
                // the only possible method of conversion
                // with minimum operations.
                if (i == 0) {
                    dp[i][j] = j;
                }

                // If str2 is empty, all characters of str1
                // are removed, which is the only possible
                //  method of conversion with minimum
                //  operations.
                else if (j == 0) {
                    dp[i][j] = i;
                }

                else {
                    // find the minimum among three
                    // operations below


                    dp[i][j] = minm_edits(dp[i - 1][j - 1]
                                    + NumOfReplacement(str1.charAt(i - 1),str2.charAt(j - 1)), // replace
                            dp[i - 1][j] + 1, // delete
                            dp[i][j - 1] + 1); // insert
                }
            }
        }

        return dp[str1.length()][str2.length()];
    }

    // check for distinct characters
    // in str1 and str2

    static int NumOfReplacement(char c1, char c2)
    {
        return c1 == c2 ? 0 : 1;
    }

    // receives the count of different
    // operations performed and returns the
    // minimum value among them.

    static int minm_edits(int... nums)
    {

        return Arrays.stream(nums).min().orElse(
                Integer.MAX_VALUE);
    }

    // Driver Code
    public static void main(String args[])
    {

        String s1 = "glomax";
        String s2 = "folmax";

        System.out.println(compute_Levenshtein_distanceDP(s1, s2));
    }

}

