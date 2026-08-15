package cn.habi.rank.common;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * S2C 排位结算推送（服务端 → 客户端）。
 * MM R/综合分按设计不对玩家展示，不在载荷中。
 *
 * @param win             本局是否胜利
 * @param factionDisplay  本局阵营显示名（"平民" 等）
 * @param rankScore       结算后 RANK 分
 * @param rankDelta       本局 RANK 变动（正/负）
 * @param tierDisplay     结算后段位显示（"B+" 等）
 * @param protectionUsed  本局是否触发升段保护
 */
public record RankUpdatePayload(
        boolean win,
        String factionDisplay,
        int rankScore,
        int rankDelta,
        String tierDisplay,
        boolean protectionUsed
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<RankUpdatePayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("habirank", "update"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RankUpdatePayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, RankUpdatePayload::win,
                    ByteBufCodecs.STRING_UTF8, RankUpdatePayload::factionDisplay,
                    ByteBufCodecs.VAR_INT, RankUpdatePayload::rankScore,
                    ByteBufCodecs.VAR_INT, RankUpdatePayload::rankDelta,
                    ByteBufCodecs.STRING_UTF8, RankUpdatePayload::tierDisplay,
                    ByteBufCodecs.BOOL, RankUpdatePayload::protectionUsed,
                    RankUpdatePayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
