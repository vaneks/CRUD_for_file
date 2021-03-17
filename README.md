# CRUD_for_file
Необходимо реализовать консольное CRUD приложение, которое имеет следующие сущности:

Team(id, name, List<Developer> developers)
Developer(id, firstName, lastName, List<Skill> skills)
Skill(id, name)
TeamStatus (enum ACTIVE, UNDER_REVIEW, DELETED)

В качестве хранилища данных необходимо использовать текстовые файлы:
teams.txt, developers.txt, skills.txt
Пользователь в консоли должен иметь возможность создания, получения, редактирования и удаления данных.
Слои:
model - POJO классы
repository - классы, реализующие доступ к текстовым файлам
controller - обработка запросов от пользователя
view - все данные, необходимые для работы с консолью

Например: Team, TeamRepository, TeamController, TeamView и т.д.
Для репозиторного слоя желательно использовать базовый интерфейс:
interface GenericRepository<T,ID>



interface TeamRepository extends GenericRepository<Team, Long>

class JavaIOTeamRepositoryImpl implements TeamRepository

Результатом выполнения задания должен быть отдельный репозиторий с README.md файлом, который содержит описание задачи, проекта и инструкции запуска приложения через командную строку.
