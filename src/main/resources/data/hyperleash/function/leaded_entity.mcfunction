advancement revoke @s only hyperleash:leaded_entity

tag @e[nbt=!{leash:{}},tag=hyperleash_leashed] remove hyperleash_leashed

execute if predicate {"condition":"minecraft:entity_properties","entity":"this","predicate":{"flags":{"is_sneaking":true}}} run function hyperleash:releash with entity @s

tag @n[nbt={leash:{}},tag=!hyperleash_leashed] add hyperleash_leashed