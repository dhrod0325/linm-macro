package lm.macro.pcap;

/**
 * Created by dhrod0325 on 2017-06-29.
 */
public class Packet {
    public static final String CHARSET = "EUC-KR";

    private byte[] data;
    private int _off = 0;
    private int result;
    private int size;

    public Packet data(byte[] data) {
        this.data = data;
        this.size = data.length;

        return this;
    }

    public Packet data(byte[] data, int length) {
        this.data = data;
        this.size = length;
        return this;
    }

    public boolean isRead(int size) {
        return _off + size <= this.size;
    }

    public int readC() {
        return data[_off++] & 0xff;
    }

    public int readH() {
        result = data[_off++] & 0xff;
        result |= data[_off++] << 8 & 0xff00;
        return result;
    }

    public int readDD() {
        int a = (data[_off] & 0xff) * 2;
        int b = (data[_off] << 8 & 0xffff);
        int r = data[_off + 1] > 1 ? (b / 3) + a : a;

        _off += 2;

        return r;
    }

    public int readD() {
        result = data[_off++] & 0xff;
        result |= data[_off++] << 8 & 0xff00;
        result |= data[_off++] << 0x10 & 0xff0000;
        result |= data[_off++] << 0x18 & 0xff000000;
        return result;
    }

    public double readF() {
        result = data[_off++] & 0xff;
        result |= data[_off++] << 8 & 0xff00;
        result |= data[_off++] << 0x10 & 0xff0000;
        result |= data[_off++] << 0x18 & 0xff000000;
        result |= (long) data[_off++] << 0x20 & 0xff00000000L;
        result |= (long) data[_off++] << 0x28 & 0xff0000000000L;
        result |= (long) data[_off++] << 0x30 & 0xff000000000000L;
        result |= (long) data[_off++] << 0x38 & 0xff00000000000000L;
        return Double.longBitsToDouble(result);
    }

    public String readS() {
        String text;
        try {
            text = new String(data, _off, size - _off, CHARSET);
            int idx = text.indexOf(0x00);
            if (idx >= 0) {
                text = text.substring(0, idx);
            }
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) >= 127) {
                    _off += 2;
                } else {
                    _off += 1;
                }
            }
            _off += 1;
        } catch (Exception e) {
            text = null;
        }
        return text;
    }

    public String readSS() {
        String text;
        int loc = 0;
        int start;
        try {
            start = _off;
            while (readH() != 0) {
                loc += 2;
            }
            StringBuilder test = new StringBuilder();
            do {
                if ((data[start] & 0xff) >= 127 || (data[start + 1] & 0xff) >= 127) {
                    byte[] t = new byte[2];
                    t[0] = data[start + 1];
                    t[1] = data[start];
                    test.append(new String(t, 0, 2, CHARSET));
                } else {
                    test.append(new String(data, start, 1, CHARSET));
                }
                start += 2;
                loc -= 2;
            } while (0 < loc);

            text = test.toString();
        } catch (Exception e) {
            text = null;
        }
        return text;
    }

    public byte[] readB() {
        byte[] BYTE = new byte[size - _off];
        System.arraycopy(data, _off, BYTE, 0, BYTE.length);
        _off += (BYTE.length + 1);
        return BYTE;
    }

    public byte[] getCurrentReadableBytes() {
        byte[] bytes = new byte[size - _off];
        System.arraycopy(data, _off, bytes, 0, bytes.length);
        return bytes;
    }
}
