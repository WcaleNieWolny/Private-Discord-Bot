package pl.wolny;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import pl.wolny.cmds.AvatarCmd;
import pl.wolny.cmds.InviteEvent;
import pl.wolny.cmds.PurgeCmd;
import pl.wolny.cmds.Rep;
import pl.wolny.events.PreventPing;

import javax.security.auth.login.LoginException;

import java.sql.*;

public class main {
    public static void main(String[] args) throws SQLException {
        try {
            DiscordBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private static void DiscordBot() throws LoginException {
        JDA jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .build();
        jda.addEventListener(new PreventPing());
        jda.addEventListener(new Rep());
        jda.addEventListener(new InviteEvent());
        jda.addEventListener(new PurgeCmd());
        jda.addEventListener(new AvatarCmd());
    }
}
