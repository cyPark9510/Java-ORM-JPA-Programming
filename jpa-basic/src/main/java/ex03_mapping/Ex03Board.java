package ex03_mapping;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
// SEQUENCE 전략
//@SequenceGenerator(
//        name           = "BOARD_SEQ_GENERATOR",
//        sequenceName   = "BOARD_SEQ",     // 매핑할 DB 시퀀스 이름
//        initialValue   = 1,
//        allocationSize = 1
//)
// TABLE 전략
//@TableGenerator(
//        name           = "BOARD_SEQ_GENERATOR",
//        table          = "MY_SEQUENCES",
//        pkColumnValue  = "BOARRD_SEQ",
//        allocationSize = 1
//)
public class Ex03Board {

    @Id
    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy  = GenerationType.SEQUENCE,
//                    generator = "BOARD_SEQ_GENERATOR")
//    @GeneratedValue(strategy  = GenerationType.TABLE,
//                    generator = "BOARD_SEQ_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "varchar(100) default 'EMPTY'")
    private String data;

    @Column(precision = 10, scale = 2)
    private BigDecimal cal;

    @Lob
    private byte[] lobByte;

    @Transient
    private String dummy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BigDecimal getCal() {
        return cal;
    }

    public void setCal(BigDecimal cal) {
        this.cal = cal;
    }

    public byte[] getLobByte() {
        return lobByte;
    }

    public void setLobByte(byte[] lobByte) {
        this.lobByte = lobByte;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}
