package cn.habi.rank.common;

/**
 * 段位枚举（V0.9 定稿）。
 * 阈值链：区 <800；C 800-1099；B 1100-1399；A 1400-1699；S 1700-1749；TOPER >=1750（另需全服前 50）。
 */
public enum RankTier {
    /** 未定级：未通过资格门槛，不进排位池 */
    UNRANKED("未定级", -1),
    /** 区：< 800 分，无小段 */
    QU("区", 0),
    /** C：800-1099，C- / C / C+ */
    C("C", 800),
    /** B：1100-1399，B- / B / B+ */
    B("B", 1100),
    /** A：1400-1699，A- / A / A+ */
    A("A", 1400),
    /** S：1700-1749（区间仅 50 分），无小段 */
    S("S", 1700),
    /** TOPER：>=1750 且全服前 50 */
    TOPER("TOPER", 1750);

    public final String display;
    /** 该段位最低分（UNRANKED/QU 无意义） */
    public final int minScore;

    RankTier(String display, int minScore) {
        this.display = display;
        this.minScore = minScore;
    }

    /** 按分数解析段位（不含 UNRANKED，资格门槛由业务层处理） */
    public static RankTier byScore(int score) {
        if (score >= 1750) return TOPER;
        if (score >= 1700) return S;
        if (score >= 1400) return A;
        if (score >= 1100) return B;
        if (score >= 800) return C;
        return QU;
    }

    /** 该段位是否划分小段（C/B/A 各 3 小段，每段 100 分） */
    public boolean hasSubTiers() {
        return this == C || this == B || this == A;
    }
}
