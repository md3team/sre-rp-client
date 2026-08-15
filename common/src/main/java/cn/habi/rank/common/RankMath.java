package cn.habi.rank.common;

/**
 * 排位数学工具：分数→段位解析、小段边界、MMR、软重置、保护判定。
 */
public final class RankMath {

    public static final int INITIAL_SCORE = 1000;
    public static final int INITIAL_ELO = 1000;
    public static final int MMR_RANK_WEIGHT_PCT = 60;
    public static final float SOFT_RESET_FACTOR = 0.7f;

    private RankMath() {}

    /** 分数 → 段位结果（含小段与显示名） */
    public static TierResult resolve(int score) {
        RankTier tier = RankTier.byScore(score);
        if (!tier.hasSubTiers()) {
            return new TierResult(tier, -1, tier.display);
        }
        int sub = Math.min(2, Math.max(0, (score - tier.minScore) / 100));
        return new TierResult(tier, sub, tier.display + subSuffix(sub));
    }

    /** 小段序号 → 后缀 */
    public static String subSuffix(int sub) {
        return switch (sub) {
            case 0 -> "-";
            case 1 -> "";
            default -> "+";
        };
    }

    /**
     * 该分数所在的"小段下界"：跌破该值即掉小段（大段下界同时也是最小小段的下界）。
     * 区段（<800）无小段，返回 0 表示无保护边界。
     */
    public static int floor(int score) {
        return resolve(score).floor();
    }

    /** 综合分（隐藏 MMR）= RANK×60% + 技术×40%，仅用于匹配，不对玩家展示 */
    public static int mmr(int rankScore, int elo) {
        return Math.round(rankScore * MMR_RANK_WEIGHT_PCT / 100.0f + elo * (100 - MMR_RANK_WEIGHT_PCT) / 100.0f);
    }

    /** 赛季软重置：新分 = 1000 + (旧分 - 1000) × 0.7 */
    public static int softReset(int score) {
        return Math.round(1000 + (score - 1000) * SOFT_RESET_FACTOR);
    }
}
