package cn.habi.rank.common;

/**
 * 分数解析出的具体段位结果。
 *
 * @param tier     大段
 * @param subIndex 小段序号：0 = "-"（如 C-），1 = 无后缀（如 C），2 = "+"（如 C+）；无小段时为 -1
 * @param display  完整显示名（"B+" / "S" / "区"）
 */
public record TierResult(RankTier tier, int subIndex, String display) {

    /** 全部小段下界（含段位边界），跌破即掉小段/掉段，用于保护判定 */
    public int floor() {
        if (!tier.hasSubTiers()) {
            return tier.minScore;
        }
        return tier.minScore + subIndex * 100;
    }
}
