#include "GameOfLife.hpp"
#include <gtest/gtest.h>

CmdLineArg &GetArguments()
{
    static CmdLineArg args = {};
    return args;
}

TEST(UniverseConstructor, DefaultNoFile)
{
    CmdLineArg &arg = GetArguments();
    srand(time(nullptr));
    int mode;
    if (arg.argc == 1)
        mode = rand() % 3 + 1; // just as example
    Universe uni(arg, mode);

    if (arg.argc == 1) // 1 режим: на вход ничего не подается, работает
    {
        EXPECT_EQ(uni.get_height(), FIELD_SIZE);
        EXPECT_EQ(uni.get_width(), FIELD_SIZE);
        EXPECT_EQ(uni.get_birth_rule(), "3");
        EXPECT_EQ(uni.get_survival_rule(), "23");

        switch (mode)
        {
        case 1:
            EXPECT_EQ(uni.size(), 49);
            EXPECT_EQ(uni.name(), "First Mode");
            EXPECT_EQ(uni.at(10, 7), true);
            EXPECT_EQ(uni.at(5, 14), true);
            EXPECT_EQ(uni.at(10, 3), false);
            break;
        case 2:
            EXPECT_EQ(uni.size(), 15);
            EXPECT_EQ(uni.name(), "Second Mode");
            EXPECT_EQ(uni.at(3, 3), true);
            EXPECT_EQ(uni.at(3, 7), true);
            EXPECT_EQ(uni.at(4, 7), false);
            break;
        case 3:
            EXPECT_EQ(uni.size(), 15);
            EXPECT_EQ(uni.name(), "Third Mode");
            EXPECT_EQ(uni.at(7, 3), true);
            EXPECT_EQ(uni.at(4, 9), true);
            EXPECT_EQ(uni.at(6, 4), false);
            break;
        default:
            break;
        }
    }
    else if ((arg.argc == 3 || arg.argc == 6) && uni.name() == "Test universe") // 2 и 3 режим, работает
    {
        EXPECT_EQ(uni.get_birth_rule(), "3");
        EXPECT_EQ(uni.get_survival_rule(), "23");
        EXPECT_EQ(uni.get_height(), 6);
        EXPECT_EQ(uni.get_width(), 6);
        EXPECT_EQ(uni.size(), 4);
    }
}

TEST(CountNeighbours, Count)
{
    CmdLineArg &arg = GetArguments();
    srand(time(nullptr));
    int mode = rand() % 3 + 1;
    if (arg.argc != 1)
        mode = 0;
    LifeGame uni(arg, mode);
    if (arg.argc == 1)
    {
        switch (mode)
        {
        case 1:
            EXPECT_EQ(uni.count_neighbours(7, 4), 1);
            break;
        case 2:
            EXPECT_EQ(uni.count_neighbours(5, 5), 3);
            break;
        case 3:
            EXPECT_EQ(uni.count_neighbours(7, 5), 3);
            break;
        default:
            break;
        }
    }
    else if (uni.name() == "Test universe")
    {
        EXPECT_EQ(uni.count_neighbours(1, 3), 3);
    }
}

/*TEST(GameStep, StepAndDisplay) // работает
{
    CmdLineArg &arg = GetArguments();
    if (arg.argc > 1)
    {
        Universe uni(arg);
        uni.display_universe();
        uni.game_step();
        uni.display_universe();
    }
}*/

TEST(LifeGame, TestingGame)
{
    CmdLineArg &arg = GetArguments();
    int mode;
    if (arg.argc == 1)
    {
        std::cout << "Since you didn't write an input file, choose one of the three default modes of the game:" << std::endl;
        std::cin >> mode;
    }
    LifeGame uni(arg, mode);

    // getting a command before starting the game
    uni.get_help();
    std::cout << "Waiting for the command: " << std::endl;
    std::string command;
    std::cin >> command;

    uni.get_tick(command, arg);

    bool game_flag = uni.get_exit(command);
    if (game_flag)
        GTEST_SKIP();

    std::ofstream write = uni.get_dump(command);
    if (write.is_open())
    {
        std::cout << "The file will be saved in a file" << std::endl;
    }

    std::cout << std::endl << "Write 'begin' to start a game or another command" << std::endl;
    std::cin >> command;
    if (command != "begin")
    {
        uni.get_tick(command, arg);

        bool game_flag = uni.get_exit(command);
        if (game_flag)
            GTEST_SKIP();

        write = uni.get_dump(command);
        if (write.is_open())
        {
            std::cout << "The file will be saved in a file" << std::endl;
        }
    }

    // starting a game
    uni.life_game(arg, write);
}

int main(int argc, char **argv)
{

    if (argc != 1 && argc != 3 && argc != 6)
    {
        throw std::runtime_error("wrong number of arguments");
    }
    CmdLineArg &arg = GetArguments();
    arg.argc = argc;
    arg.argv = argv;
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}

/*
1 режим: на вход ничего не подается, вывод на экран
            make && ./lab2

2 режим: на вход подается файл со вселенной, вывод на экран
            make && ./lab2 -i test.txt        argc = 3

3 режим: на вход подается количество итераций, входной файл со вселенной, выходной файл
            make && ./lab2 10 -i test.txt -o out.txt       argc = 6

*/