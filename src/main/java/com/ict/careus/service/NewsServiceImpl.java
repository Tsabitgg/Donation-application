package com.ict.careus.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ict.careus.dto.request.NewsRequest;
import com.ict.careus.enumeration.ETopic;
import com.ict.careus.model.News;
import com.ict.careus.model.Topic;
import com.ict.careus.repository.NewsRepository;
import com.ict.careus.repository.TopicRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService{

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public News createNews(NewsRequest newsRequest) throws BadRequestException {
        Topic topic = topicRepository.findById(newsRequest.getTopic().getId()).orElseThrow(()-> new BadRequestException("Topic not found"));
        News news = modelMapper.map(newsRequest, News.class);
        news.setTopic(topic);

        if (newsRequest.getImage() != null && !newsRequest.getImage().isEmpty()) {
            try {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(
                        newsRequest.getImage().getBytes(),
                        ObjectUtils.emptyMap());
                String imageUrl = uploadResult.get("url").toString();
                news.setImage(imageUrl);

            } catch (IOException e) {
                throw new BadRequestException("Error uploading image", e);
            }
        }

        return newsRepository.save(news);
    }

    @Override
    public News updateNews(long id, NewsRequest newsRequest) throws BadRequestException {
        News updatedNews = newsRepository.findById(id);
        if (updatedNews != null){
            updatedNews.setTitle(newsRequest.getTitle());
            updatedNews.setContent(newsRequest.getContent());
            updatedNews.setDate(newsRequest.getDate());

            Topic topic = topicRepository.findById(newsRequest.getTopic().getId()).orElseThrow(()-> new BadRequestException("Topic not found"));
            updatedNews.setTopic(topic);

            if (newsRequest.getImage() != null && !newsRequest.getImage().isEmpty()) {
                try {
                    Map<?, ?> uploadResult = cloudinary.uploader().upload(
                            newsRequest.getImage().getBytes(),
                            ObjectUtils.emptyMap());
                    String imageUrl = uploadResult.get("url").toString();
                    updatedNews.setImage(imageUrl);

                } catch (IOException e) {
                    throw new BadRequestException("Error uploading image", e);
                }
            }
        }
        return newsRepository.save(updatedNews);
    }

    @Override
    public void deleteNews(long id) throws BadRequestException {
        News deletedNews = newsRepository.findById(id);
        if (deletedNews == null) {
            throw new BadRequestException("News not found");
        }
        newsRepository.delete(deletedNews);
    }


    @Override
    public Page<News> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public Optional<News> getNewsById(long id) {
        return Optional.ofNullable(newsRepository.findById(id));
    }

    @Override
    public Page<News> getNewsByTopicName(String topicName, Pageable pageable) {
        return newsRepository.findByTopicName(ETopic.valueOf(topicName.toUpperCase()), pageable);
    }
}