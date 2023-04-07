from words import Words

def longest_common_substring(word, suggestions):
    # longest common substring suggestions - the return value
    lcs_suggestions = []
    
    # generate all substrings and store them in a dictionary
    # dictionary key is the length of the substring, value is the substring
    all_substrings={}
    for i in range(len(word),-1,-1):
        for j in range(0, i):
            substring = word[j:i]
            if len(substring) in all_substrings:
                all_substrings[len(substring)].append(substring)
            else:
                all_substrings[len(substring)]=[substring]
    
    # starting with the longest substring, see if there are any words in the suggestions
    # list that contain this substring
    longest_substring_length = max(all_substrings)

    for i in range(longest_substring_length,0,-1):
        for substring in all_substrings[i]:
            for suggestion in suggestions:
                if substring in suggestion:
                    lcs_suggestions.append(suggestion)
        if len(lcs_suggestions) > 0:
            break
    
    return lcs_suggestions

english_words = Words('english.txt')
sentence = input('Enter a sentence: ')
word_list = sentence.split(' ')
for word in word_list:
    suggestions = []
    if not english_words.is_word(word.strip()):
        print('correcting', word)
        corrections = english_words.get_replace_corrections(word)
        suggestions+=corrections
        print('Replace suggestions: ', corrections)
        corrections = english_words.get_delete_corrections(word)
        suggestions+=corrections
        print('Delete suggestions: ', corrections)
        corrections = english_words.get_add_corrections(word)
        suggestions+=corrections
        print('Add suggestions: ', corrections)
        corrections = longest_common_substring(word, suggestions)

        print("Narrowed down suggestions ", corrections)
