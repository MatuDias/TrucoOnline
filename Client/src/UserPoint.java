public class UserPoint
{
    private byte point;

    public UserPoint()
    {
        point = 0;
    }

    public void addPoint(byte howMany)
    {
        switch (howMany)
        {
            case 1 -> this.point += 1;
            case 3 -> this.point += 3;
            case 6 -> this.point += 6;
            case 9 -> this.point += 9;
            case 12 -> this.point += 12;
        }
    }

    public void resetPoint()
    {
        setPoint((byte)0);
    }

    public byte getPoint() {
        return point;
    }

    public void setPoint(byte point) {
        this.point = point;
    }
}
