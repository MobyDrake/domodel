package ru.geekbrains.domodel.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.geekbrains.domodel.dto.NewsDto;
import ru.geekbrains.domodel.dto.NewsRequestDto;
import ru.geekbrains.domodel.entities.News;
import ru.geekbrains.domodel.mappers.NewsMapper;
import ru.geekbrains.domodel.repositories.NewsRepository;
import ru.geekbrains.domodel.services.api.NewsService;
import ru.geekbrains.domodel.services.api.UserService;

import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.geekbrains.domodel.entities.constants.Roles.ROLE_ADMIN;

/**
 * Реализация сервиса новостей
 */
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final UserService userService;
    private final NewsMapper newsMapper;

    // Репозиторий новостей
    private final NewsRepository newsRepository;

    @Override
    public NewsDto getById(Long id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        return optionalNews.map(newsMapper::newsToNewsDto).orElse(null);
    }

    //todo добавить секьюрити
    @Override
    //Архив новостей
    public List<NewsDto> getArchive(int page) {
//        List<News> newsArchive = getAllVisibleNews();
//        if (authentication.getName().equals("admin")) newsArchive.addAll(getDeletedNews());
//        return newsArchive;
//        PageRequest pageable = new PageRequest(1, 2);
//        List<News> newsArchive = new ArrayList<>();
//        Page<News> page = newsRepository.findAll(pageable);
//        if (authentication.getName().equals("admin")) newsArchive.addAll(readDeletedNews());
//        for (int i = 0; i <= page.getTotalPages(); i++) {
//            List<News> listPage = newsRepository.findAll(pageable.next()).getContent();
//            newsArchive.addAll(listPage);
//        }
        List<News> newsArchive = getAllVisibleNews();
        List<NewsDto> newsDtoList = new ArrayList<>();
        int counter = 0;
        int limit = 2; //количество новостей на странице
        for (int i = 0 + (page - 1) * limit; i < newsArchive.size(); i++) {
            newsDtoList.add(newsMapper.newsToNewsDto(newsArchive.get(i)));
            counter++;
            if (counter == limit) break;
        }
        return newsDtoList;
    }

    @Override
    public List<NewsDto> getAllRelevant(Authentication authentication) {
        Stream<NewsDto> newsDtoStream = newsRepository.findAll().stream().map(newsMapper::newsToNewsDto);
        // Если пользователь не авторизован
        if (authentication == null) {
            return newsDtoStream.filter(n -> !n.isHidden() && !n.isVisible()).collect(Collectors.toList());
        }
        // Если пользователь - админ
        if (hasAuthenticationRoleAdmin(authentication)) {
//            return newsDtoStream.limit(10).collect(Collectors.toList());
        } else { // Просто пользователь
//            return newsDtoStream.filter(n -> !n.isHidden()).collect(Collectors.toList());
        }
        // TODO продолжить метод
//        List<News> allNews = new ArrayList<News>()
//        if (!authentication.isAuthenticated()) allNews = getPublicNews();
//        else allNews = getAllVisibleNews();
//        //if (authentication == null) newsRelevant = readPublicNews();
//        //else newsRelevant = readAllVisibleNews();
//        //if (newsRelevant.size() > 10) newsRelevant.subList(0, 9);
//        int limit = 3;
//        if (allNews.size() < limit) limit = allNews.size();
//        for (int i = 0; i < limit; i++) {
//            newsRelevant.add(newsMapper.newsToNewsDto(allNews.get(i)));
//        }
//        return newsRelevant;
//        return allNews.stream().map(newsMapper::newsToNewsDto).collect(Collectors.toList());
        return null;
    }

    // РЕДАКТИРОВАНИЕ
//todo добавить секьюрити
    @Override
    public boolean updateVisibilityNewsById(Long id, boolean visible) {
        //удаляем новость, меняя ее видимость и удаляя ее из закрепленных
        News news = newsRepository.getOne(id);
        if (!visible) getById(id).setPinned(false);
        news.setVisible(visible);
        newsRepository.save(news);
        return visible;
    }

    // TODO учесть, что закрепленных может быть только 2
    @Override
    public NewsDto updateNewsById(Long id, NewsDto newsDto) {
        News news = newsRepository.getOne(id);
        news.setCreationDate(LocalDate.now());
        news.setTitle(newsDto.getTitle());
        news.setFullText(newsDto.getFullText());
        news.setPictureLink(newsDto.getPictureLink());
        news.setHidden(newsDto.isHidden());
        news.setPinned(newsDto.isPinned());
        news.setVisible(newsDto.isVisible());
        newsRepository.save(news);
        NewsDto newNewsDto = getById(id);
        return newNewsDto;
    }

    @Override
    public boolean updatePinningNewsById(Long id, boolean pinned) {
        //Закрепляем выбранную новость,если закрепленных уже 2, то одну открепляем
        News news = newsRepository.getOne(id);
        if (news.isPinned() == pinned) return pinned;
        List<News> pinnedNews = getPinnedNews();
        if (pinnedNews.size() > 1 && pinned) pinnedNews.get(1).setPinned(false);
        newsRepository.save(pinnedNews.get(1));
        news.setPinned(pinned);
        newsRepository.save(news);
        return pinned;
    }


    //ДОПОЛНИТЕЛЬНЫЕ МЕТОДЫ
    //ЧТЕНИЕ

    @Override
    public NewsDto save(NewsRequestDto newsRequestDto, Authentication authentication) {
        // Если пользователь не авторизован или не админ
        if (authentication == null || !hasAuthenticationRoleAdmin(authentication)) {
            return null;
        }
        News news = newsMapper.newsRequestDtoToNews(newsRequestDto);
        // Добавляем дату
        news.setCreationDate(LocalDate.now());
        // Добавляем автора новости
        news.setAuthorId(userService.getUserByUsername(authentication.getName()));
        return newsMapper.newsToNewsDto(newsRepository.save(news));
    }

    //получение поcледней новости
    @Override
    public News getLast() {
        List<News> newsList = getAllNews();
        if (!newsList.isEmpty()) {
            return newsList.get(0);
        }
        return null;
    }

    @Override
    public List<NewsDto> getAll() {
        List<News> newsList = newsRepository.findAll();
        Collections.reverse(newsList);
        return newsList.stream()
                .map(newsMapper::newsToNewsDto)
                .collect(Collectors.toList());
    }

    public List<News> getAllNews() {
        List<News> newsList = newsRepository.findAll();
        Collections.reverse(newsList);
        return newsList;
    }

    private Page<News> getAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }


    //РЕДАКТИРОВАНИЕ

    private List<News> getAllVisibleNews() {
        //список неудаленных новостей для зарегистрированных пользователей. Сначала закрепленные, потом остальные по дате от новых к старым
        List<News> allNewsList = getAllNews();
        //TODO я уже начал
        List<News> pinnedNewsList = getPinnedNews();
        List<News> newsList = new ArrayList<News>();
        for (int i = 0; i < pinnedNewsList.size(); i++) { //добавляем закрпленные
            newsList.add(pinnedNewsList.get(i));
        }

        for (int i = 0; i < allNewsList.size(); i++) { //добавляем остальные неудаленные новости
            if (allNewsList.get(i).isVisible() && !allNewsList.get(i).isPinned()) {
                newsList.add(allNewsList.get(i));
            }
        }
        return newsList;
    }

    private List<News> getDeletedNews() {
        //список удаленных новостей
        List<News> allNewsList = getAllNews();
        List<News> newsList = new ArrayList<News>();
        for (int i = 0; i < allNewsList.size(); i++) {
            if (!allNewsList.get(i).isVisible()) {
                newsList.add(allNewsList.get(i));
            }
        }
        return newsList;
    }

    private List<News> getPinnedNews() {
        //возвращает лист из 2 закрепленных новостей
        List<News> allNewsList = getAllNews();
        List<News> newsList = new ArrayList<>();
        byte counter = 0;
        for (int i = 0; i < allNewsList.size(); i++) {
            if (allNewsList.get(i).isPinned()) {
                newsList.add(allNewsList.get(i));
                counter++;
            }
            if (counter == 2) break;
        }
        return newsList;
    }

    private List<News> getPublicNews() {
        //список новостей (10 штук) для незарегистрированных пользователей. Сначала закрепленные, потом остальные по дате от новых к старым
        List<News> allNewsList = getAllNews();
        List<News> pinnedNewsList = getPinnedNews();
        List<News> newsList = new ArrayList<>();
        byte counter = 0;
        for (int i = 0; i < pinnedNewsList.size(); i++) { //добавляем закрепленные, если они публичные
            if (pinnedNewsList.get(i).isHidden()) {
                newsList.add(pinnedNewsList.get(i));
                counter++;
            }
        }
        for (int i = 0; i < allNewsList.size(); i++) { //добавляем обычные новости до 10, если они видимы, публичны и незакреплены
            if (allNewsList.get(i).isHidden() && allNewsList.get(i).isVisible() && !allNewsList.get(i).isPinned()) {
                newsList.add(allNewsList.get(i));
                counter++;
            }
            if (counter == 10) break;
        }
        return newsList;
    }

    /**
     * Проверить, что пользователь имеет роль Админа
     */
    private boolean hasAuthenticationRoleAdmin(Authentication authentication) {
        return (authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals(ROLE_ADMIN)));
    }
}