package tools;

import javax.swing.*;

public class Token {
    int x, y;
    int row, col;
    int type;

    boolean deleteFlag = false;
    boolean refillFlag = false;

    public void setType(int t) {
        this.type = t;
    }
    public void setRow(int r) {
        this.row = r;
    }
    public int getRow() {
        return this.row;
    }
    public void setCol(int c) {
        this.col = c;
    }
    public int getCol() {
        return this.col;
    }
    public int getType() {
        return this.type;
    }
    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean getDeleteFlag() {
        return deleteFlag;
    }
    public void setDeleteFlag(boolean f) {
        this.deleteFlag = f;
    }
    public boolean getRefillFlag()
    {
        return refillFlag;
    }
    public void setRefillFlag(boolean f)
    {
        this.refillFlag = f;
    }

}