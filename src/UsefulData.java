public class UsefulData {
    /**
     * X de translation
     * 
     */
    private int addX;
    /**
     * Y de translation
     */
    private int addY;
    /**
     * longeur
     */
    private int LCLength;

    public UsefulData(int addX, int addY, int LCLength) {
        this.addX = addX;
        this.addY = addY;
        this.LCLength = LCLength;
    }

    public int getAddX() {
        return addX;
    }

    public void setAddX(int addX) {
        this.addX = addX;
    }

    public int getAddY() {
        return addY;
    }

    public void setAddY(int addY) {
        this.addY = addY;
    }

    public int getLCLength() {
        return LCLength;
    }

    public void setLCLength(int lCLength) {
        LCLength = lCLength;
    }

}
