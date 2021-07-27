package com.example.libraryapp;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    public List<AuthorDto> listAuthors() {
        List<Author> all = authorRepository.findAll();
        Type targetListType = new TypeToken<List<AuthorDto>>() {
        }.getType();

        return mapper.map(all, targetListType);
    }

    public AuthorDto createAuthor(CreateAuthorCommand command) {
        Author save = authorRepository.save(new Author(command.getName()));
        return mapper.map(save, AuthorDto.class);
    }

    @Transactional
    public AuthorDto addBook(Long id, CreateBookCommand command) {
        Author author = findAuthorById(id);
        Book book = bookRepository.save(new Book(command.getIsbn(), command.getTitle()));
        author.addBook(book);
        return mapper.map(author, AuthorDto.class);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    private Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }
}
