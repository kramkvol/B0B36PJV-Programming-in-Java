package cz.cvut.fel.pjv;

public class BruteForceAttacker extends Thief {

    @Override
    public void breakPassword(int sizeOfPassword) {
        // list that make up the password
        char[] allSymbols = getCharacters();
        char[] currentArr = new char[sizeOfPassword];
        breakPasswordRecursion(allSymbols, sizeOfPassword, currentArr, 0);
    }
    private void breakPasswordRecursion(char[] allSymbols, int sizeOfPassword,char[] currentArr, int counter) {
        if (counter == sizeOfPassword) {
            tryOpen(currentArr);
            return;
        }
        for (char symbol : allSymbols) {
            currentArr[counter] = symbol;
            breakPasswordRecursion(allSymbols, sizeOfPassword, currentArr, counter + 1);
            if (isOpened()) {
                return;
            }
        }
    }
}



