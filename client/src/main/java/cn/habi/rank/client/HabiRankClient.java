package cn.habi.rank.client;

import cn.habi.rank.common.RankUpdatePayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Habi 排位系统 —— 客户端模组入口。
 * 纯展示：零算分，只接收服务端推送的结算结果。
 * 不安装本模组也能正常游玩（服务端照常算分，只是没有 UI）。
 */
public class HabiRankClient implements ClientModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("habirank-client");

    @Override
    public void onInitializeClient() {
        // 客户端也必须注册 S2C 载荷类型（Fabric 要求收发两侧各自注册）
        PayloadTypeRegistry.playS2C().register(RankUpdatePayload.TYPE, RankUpdatePayload.STREAM_CODEC);

        ClientPlayNetworking.registerGlobalReceiver(RankUpdatePayload.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                Minecraft mc = context.client();
                if (mc.player == null) {
                    return;
                }
                String deltaText = payload.rankDelta() >= 0
                        ? "§a+" + payload.rankDelta()
                        : "§c" + payload.rankDelta();
                String resultText = payload.win() ? "§6胜利" : "§7失败";
                // 动作栏即时反馈（M5 将升级为 HUD 段位徽章）
                mc.player.displayClientMessage(Component.literal(String.format(
                                "§6[HabiRank] %s §7| %s §f%s分 §7→ §e%d§7（%s）",
                                resultText, payload.factionDisplay(), deltaText,
                                payload.rankScore(), payload.tierDisplay())),
                        true);
                if (payload.protectionUsed()) {
                    mc.gui.getChat().addMessage(Component.literal("§6[HabiRank] §e升段保护生效：本局不掉段。"));
                }
            });
        });
        LOGGER.info("[HabiRank] 客户端就绪（纯展示，零算分）");
    }
}
