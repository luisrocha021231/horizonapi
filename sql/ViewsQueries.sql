create or replace view horizonapi.v_characters as
	select
		c.id  as id,
		c.slug as slug,
		c.name as name,
		coalesce(c.image_path, 'No Data') as image_path,
		coalesce(c.culture, 'No Data') as culture,
		coalesce(c.gender, 'No Data') as gender,
		coalesce(c.profession, 'No Data') as profession,
		coalesce(c.status, 'No Data') as status,
		coalesce(c.eyes_color, 'No Data') as eyes_color,
		coalesce(c.hair_color, 'No Data') as hair_color,
		coalesce(c.birth_date, 'No Data') as birth_date,
		coalesce(c.death_date, 'No Data') as death_date,
		coalesce(json_agg(distinct g.name) filter (where g.name  is not null), '[]') as appearances
	from
		horizonapi.characters c
	left join 
		horizonapi.character_game cg
	on
		cg.id_character = c.id 
	left join
	 	horizonapi.game g 
	on
		g.id = cg.id_game 
	group by
		c.id,
		c.slug,
		c.name,
		c.image_path,
		c.culture,
		c.gender,
		c.profession,
		c.status,
		c.eyes_color,
		c.hair_color,
		c.birth_date,
		c.death_date
	order by 
		c.id asc;

--- --------------------------------------------------------
CREATE OR REPLACE VIEW horizonapi.v_areas AS
SELECT 
    a.id AS id,
    a.slug AS slug,
    a.name AS name,
    COALESCE(a.description, 'No data') AS description,
    COALESCE(a.type, 'No data') AS type,
    COALESCE(a.faction, 'No data') AS faction,
    COALESCE(a.image_path, 'No data') AS image_path,
    COALESCE(json_agg(DISTINCT r.name) FILTER (WHERE r.name IS NOT NULL), '[]') AS regions,
    COALESCE(json_agg(DISTINCT g.name) FILTER (WHERE g.name IS NOT NULL), '[]') AS appears_in
FROM 
    horizonapi.area a
LEFT JOIN
    horizonapi.region r ON r.id = a.id
LEFT JOIN
    horizonapi.area_game ag ON ag.id_area = a.id
LEFT JOIN
    horizonapi.game g ON g.id = ag.id_game
WHERE
    a.slug <> 'unknown'
GROUP BY
    a.id, a.slug, a.name, a.description, a.type, a.faction, a.image_path
ORDER BY
    a.id ASC;

--- --------------------------------------------------------
create or replace view horizonapi.v_regions as
select
	r.id,
	r.slug,
	r.name,
	r.description,
	r.image_path,
	COALESCE(json_agg(DISTINCT rf.name_faction) FILTER (WHERE rf.name_faction IS NOT NULL), '[]') AS factions,
	a.name as area,
	COALESCE(json_agg(DISTINCT g.name) FILTER (WHERE g.name IS NOT NULL), '[]') AS appears_in
from
	horizonapi.region r	
left join
	horizonapi.area a
on r.id_area = a.id
left join
	horizonapi.region_faction rf 
on rf.id_region = r.id 
left join
	horizonapi.area_game ag
on ag.id_area = a.id
left join
	horizonapi.game g
on g.id = ag.id_game
group by
	r.id, r.slug, r.name, r.description, r.image_path, a.name
order by 
	r.id asc;

--- --------------------------------------------------------
create or replace view horizonapi.v_cauldron as
select
	c.id,
	c.slug,
	c.name,
	coalesce(c.description, 'No data') as description,
	coalesce(c.path_image , 'No data' ) as path_image,
	coalesce(c.map_location, 'No data') as map_location_image,
	coalesce(r.name, 'No data') as region,
	coalesce(json_agg(distinct m.name) filter (where m.name is not null), '[]') as overridden_machines,
	coalesce(json_agg(distinct g.name) filter (where g.name is not null), '[]') as appears_in
from
	horizonapi.cauldron c
left join
	horizonapi.region r
on
	r.id = c.id_region 
left join
	horizonapi.area a
on
	a.id = r.id_area
left join
	horizonapi.area_game ag
on 
	ag.id_area = a.id 
left join
	horizonapi.game g
on
	g.id = ag.id_game
left join
	horizonapi.machine_cauldron mc
on
	mc.id_cauldron = c.id 
left join
	horizonapi.machines m
on
	mc.id_machine = m.id
group by
	c.id,
	c.slug,
	c.name,
	c.description,
	c.path_image,
	c.map_location,
	r.name
order by
	c.id asc;

--- --------------------------------------------------------
create or replace view horizonapi.v_machines as
select
	m.id,
	m.slug,
	m.name,
	coalesce(m.path_image, 'No data') as path_image,
	coalesce(m.size, 'No data') as size,
	coalesce(m.weight, 'No data') as weight,
	coalesce(m.origin, 'No data') as origin,
	coalesce(m.class, 'No data') as class,
	coalesce(json_agg(distinct ms.strength_name) filter (where ms.strength_name is not null), '[]') as strength,
	coalesce(json_agg(distinct mw.weakness_name) filter (where mw.weakness_name is not null), '[]') as weakness,
	coalesce(json_agg(distinct mec.explosive_components) filter (where mec.explosive_components is not null), '[]') as explosive_components,
	coalesce(json_agg(distinct mwp.weak_point_name) filter (where mwp.weak_point_name is not null), '[]') as weak_points,
	coalesce(json_agg(distinct g.name) filter (where g.name is not null), '[]') as appears_in
from
	horizonapi.machines m 
left join
	horizonapi.machine_game mg 
on
	mg.id_machine = m.id 
left join
	horizonapi.game g
on
	g.id = mg.id_game
left join
	horizonapi.machine_strength ms
on
	ms.id_machine = m.id 
left join
	horizonapi.machine_weakness mw
on
	mw.id_machine = m.id 
left join
	horizonapi.machine_explosive_components mec
on
	mec.id_machine = m.id 
left join
	horizonapi.machine_weak_points mwp 
on
	mwp.id_machine = m.id
group by
	m.id,
	m.slug,
	m.path_image,
	m.size,
	m.weight,
	m.origin,
	m.class
order by
	m.id asc;