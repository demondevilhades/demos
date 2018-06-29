
SELECT @rownum:=@rownum+1 as row_number, id FROM test_table, (SELECT @rownum:=0) r ORDER BY id DESC ;

-- 180
select num0 as ConsecutiveNums from (
select l0.num as num0, l1.num as num1, l2.num as num2 from Logs l0 
left join Logs l1 on l0.id + 1 = l1.id 
left join Logs l2 on l1.id + 1 = l2.id 
where l0.num = l1.num and l0.num = l2.num 
) l group by l.num0
; 

-- 181
--select e1.name as Employee from Employee e1 
--left join Employee e2 on e1.ManagerId = e2.Id 
--where e2.id is not null and e1.ManagerId != "NULL" and e1.Salary > e2.Salary;

select e1.Name as Employee
from Employee as e1 inner join Employee as e2 
where e1.ManagerId = E2.Id and E1.Salary > E2.Salary

-- 182

select Email from 
(select Email, count(Id) as counter from Person 
group by Email
having counter > 1) t
;

-- 183
select c.Name as Customers from Customers c 
where c.Id not in (
select CustomerId from Orders 
)

--184
select d.Name as Department, e1.Name as Employee, e1.Salary as Salary from Employee e1 inner join ( 
select e.DepartmentId, max(e.Salary) as maxSalary from Employee e group by e.DepartmentId 
) e2 on e1.DepartmentId = e2.DepartmentId and e1.Salary = e2.maxSalary 
left join Department d on e1.DepartmentId = d.Id 
where d.Name is not null 
;

--185
select d.Name as Department, e.Name as Employee, e.Salary as Salary 
from Employee e 
inner join Department d 
on e.DepartmentId = d.Id 
where (select count(distinct(e1.Salary)) from Employee e1 where e1.Salary > e.Salary and e1.DepartmentId = e.DepartmentId) < 3 
;

--196
--delete from Person p 
--where p.Id not in (
--select min(p1.Id) from Person p1 
--group by p1.Email 
--);

delete from Person p 
inner join p1 
on p.Email = p1.Email 
where p.Id > p1.Id
;

--197
select w.Id from Weather w 
inner join Weather w1 on TO_DAYS(w.Date) - 1 = TO_DAYS(w1.Date) 
where w.Temperature > w1.Temperature;

--262

select r2.Day as Day, round((IFNULL(r1.counter1,0) / r2.counter2), 2) as 'Cancellation Rate' from 
(
select a2.Request_at as Day, count(a2.Id) as counter2 from 
(
select t2.* from Trips t2 
inner join Users u2 on t2.Client_Id = u2.Users_Id 
where u2.Banned = 'No' 
and TO_DAYS(t2.Request_at) >= TO_DAYS('2013-10-01') and TO_DAYS(t2.Request_at) <= TO_DAYS('2013-10-03') 
) a2 
group by a2.Request_at
) r2
left join 
(
select a1.Request_at as Day, count(a1.Id) as counter1 from 
(
select t1.* from Trips t1 
inner join Users u1 on t1.Client_Id = u1.Users_Id 
where u1.Banned = 'No' 
and TO_DAYS(t1.Request_at) >= TO_DAYS('2013-10-01') and TO_DAYS(t1.Request_at) <= TO_DAYS('2013-10-03') 
and t1.Status in ('cancelled_by_driver', 'cancelled_by_client') 
) a1 
group by a1.Request_at 
) r1
on r1.Day = r2.Day
;

--569 todo
select e1.Company, count(e1.Id) from Employee e1 
group by e1.Company 
;

select e.Id, e.Company, e.Salary 
from Company e, Company d 
where e.Company = d.Company 
group by e.Company, e.Salary 
having sum(case when e.Salary = d.Salary then 1 else 0 end)>= abs(sum(sign(e.Salary - d.Salary)))
;


--median
select user_id,avg(price)
  from (
       select e.user_id, e.price
       from producte e, producte d
       where e.user_id = d.user_id
       group by e.user_id, e.price
       having sum(case when e.price = d.price then 1 else 0 end)>= abs(sum(sign(e.price - d.price)))
      )t
   group by user_id
;
