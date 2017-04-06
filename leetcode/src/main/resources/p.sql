
-- 180
select num0 as ConsecutiveNums from (
select l0.num as num0, l1.num as num1, l2.num as num2 from Logs l0 
left join Logs l1 on l0.id + 1 = l1.id 
left join Logs l2 on l1.id + 1 = l2.id 
where l0.num = l1.num and l0.num = l2.num 
) l group by l.num0
; 

-- 181


-- 182

select Email from 
(select Email, count(Id) as counter from Person 
group by Email
having counter > 1) t
;
