package cn.habi.rank.common;

import java.util.EnumMap;
import java.util.Map;

/**
 * V0.9 定稿加分表（2000 人 × 1000 局蒙特卡洛校准）。
 *
 * <pre>
 * 段位    杂/平民/警长   中立        偏狼        盈亏平衡胜率
 * 区      +70 / -20     +80 / -20   +70 / -15   22%
 * C       +60 / -25     +70 / -25   +60 / -20   29%
 * B       +30 / -30     +40 / -30   +30 / -25   50%  ← 普通玩家稳态落点 B~B+
 * A       +25 / -35     +30 / -35   +25 / -35   58%
 * S       +20 / -35     +25 / -35   +20 / -35   64%
 * TOPER   +15 / -45     +20 / -45   +15 / -40   75%
 * </pre>
 *
 * 查表规则：按【结算前】所处段位取行，按阵营加分列取列。
 */
public final class ScoreTable {

    /** 段位 → 加分列 → {胜, 负}（负值存正数，取用时加负号） */
    private static final Map<RankTier, Map<Faction.Column, int[]>> TABLE = new EnumMap<>(RankTier.class);

    static {
        put(RankTier.QU, 70, 20, 80, 20, 70, 15);
        put(RankTier.C, 60, 25, 70, 25, 60, 20);
        put(RankTier.B, 30, 30, 40, 30, 30, 25);
        put(RankTier.A, 25, 35, 30, 35, 25, 35);
        put(RankTier.S, 20, 35, 25, 35, 20, 35);
        put(RankTier.TOPER, 15, 45, 20, 45, 15, 40);
    }

    private static void put(RankTier tier, int stdWin, int stdLoss, int neuWin, int neuLoss, int wlWin, int wlLoss) {
        Map<Faction.Column, int[]> row = new EnumMap<>(Faction.Column.class);
        row.put(Faction.Column.STANDARD, new int[]{stdWin, stdLoss});
        row.put(Faction.Column.NEUTRAL, new int[]{neuWin, neuLoss});
        row.put(Faction.Column.WOLF_LEANING, new int[]{wlWin, wlLoss});
        TABLE.put(tier, row);
    }

    /**
     * 查表取分值变动。
     *
     * @param tier 结算前段位
     * @param col  阵营加分列
     * @param win  本局是否胜利
     * @return 正数加分或负数扣分
     */
    public static int delta(RankTier tier, Faction.Column col, boolean win) {
        int[] cell = TABLE.get(tier).get(col);
        return win ? cell[0] : -cell[1];
    }
}
