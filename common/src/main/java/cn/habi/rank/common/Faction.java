package cn.habi.rank.common;

/**
 * 排位阵营（6 计数类 + 1 特殊类）。
 *
 * <ul>
 *   <li>杂 —— 狼阵营（加分表按"杂"列，同平民/警长）</li>
 *   <li>平民 —— 好人阵营</li>
 *   <li>警长 —— 独立阵营，结算按平民档，计入阵营 5 次门槛</li>
 *   <li>中立 —— 第三方阵营，加分表独立一列（V0.9 保留加权）</li>
 *   <li>偏狼 —— 偏向狼方</li>
 *   <li>中立偏狼 —— 身份归中立（按中立列结算），但【不计入】阵营门槛与计数</li>
 * </ul>
 */
public enum Faction {
    WOLF("杂", Column.STANDARD, true),
    VILLAGER("平民", Column.STANDARD, true),
    SHERIFF("警长", Column.STANDARD, true),
    NEUTRAL("中立", Column.NEUTRAL, true),
    WOLF_LEANING("偏狼", Column.WOLF_LEANING, true),
    /** 中立偏狼：按中立结算，不计数 */
    NEUTRAL_WOLF_LEANING("中立偏狼", Column.NEUTRAL, false);

    /** 加分表的三列 */
    public enum Column { STANDARD, NEUTRAL, WOLF_LEANING }

    public final String display;
    public final Column column;
    /** 是否计入阵营场次门槛（中立偏狼不计入） */
    public final boolean countsTowardGate;

    Faction(String display, Column column, boolean countsTowardGate) {
        this.display = display;
        this.column = column;
        this.countsTowardGate = countsTowardGate;
    }

    public static Faction parse(String name, Faction fallback) {
        if (name == null) return fallback;
        try {
            return Faction.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }
}
