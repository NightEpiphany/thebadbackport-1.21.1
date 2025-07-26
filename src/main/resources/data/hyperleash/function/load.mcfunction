tellraw @a ["",{"text":"\nHyperLeash v2.0 LOADED!","color":"yellow"},{"text":"\nData Pack by blockerlocker","color":"yellow"},{"text":"\n\n[YouTube]","underlined":true,"color":"red","clickEvent":{"action":"open_url","value":"https://www.youtube.com/c/BlockerLockerYT"}},{"text":" ","underlined":false},{"text":"[Modrinth]","underlined":true,"color":"green","clickEvent":{"action":"open_url","value":"https://modrinth.com/user/blockerlocker"}},{"text":" ","underlined":false},{"text":"[Planet Minecraft]","underlined":true,"color":"dark_green","clickEvent":{"action":"open_url","value":"https://www.planetminecraft.com/member/blockerlocker/"}},{"text":" ","underlined":false},{"text":"[Discord]\n","underlined":true,"color":"aqua","clickEvent":{"action":"open_url","value":"https://discord.gg/EBEBtBVKCK"}}]

scoreboard objectives add hyperleash_used_firework minecraft.used:minecraft.firework_rocket

scoreboard players set @a hyperleash_used_firework 0

advancement revoke @a only hyperleash:leaded_entity
advancement revoke @a only hyperleash:used_firework_rocket
advancement revoke @a only hyperleash:used_crossbow