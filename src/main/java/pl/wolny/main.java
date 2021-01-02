package pl.wolny;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import pl.wolny.cmds.AvatarCmd;
import pl.wolny.cmds.PurgeCmd;
import pl.wolny.cmds.warns.WarnCmd;
import pl.wolny.cmds.warns.WarnsCmd;
import pl.wolny.events.MessageReciveEvent;

import javax.security.auth.login.LoginException;
import pl.wolny.cmds.ticket.*;

import java.sql.*;

public class main {
    public static void main(String[] args) throws SQLException {
        Class c = main.class;
        String className = c.getName();
        System.out.println("The fully-qualified name of the class is: " + className);
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        try {
            DiscordBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private static void DiscordBot() throws LoginException {
        Dotenv dotenv = Dotenv.load();
        if(dotenv.get("LOL-BOT").equalsIgnoreCase("true")){
            JDA jda = JDABuilder.createDefault(dotenv.get("TOKEN"))
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
                    .enableIntents(GatewayIntent.GUILD_PRESENCES)
                    .build();
            jda.addEventListener();
        }else {
            JDA jda = JDABuilder.createDefault(dotenv.get("TOKEN"))
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
                    .enableIntents(GatewayIntent.GUILD_PRESENCES)
                    .build();
            jda.addEventListener(new MessageReciveEvent());
            jda.addEventListener(new PurgeCmd());
            jda.addEventListener(new AvatarCmd());
            jda.addEventListener(new WarnsCmd());
            jda.addEventListener(new WarnCmd());
            jda.addEventListener(new CreateTicket());
            jda.addEventListener(new ReactEvent());
            jda.addEventListener(new AddUserToTicket());
        }
    }

}