#include "GameOfLife.hpp"

Universe::Universe(CmdLineArg &arg, int mode)
{
    std::string path_name;

    if (arg.argc == 1)
    {
        this->height = FIELD_SIZE;
        this->width = FIELD_SIZE;
        switch (mode)
        {
        case 1:
            path_name = "TemplateGame1.txt";
            break;
        case 2:
            path_name = "TemplateGame2.txt";
            break;
        case 3:
            path_name = "TemplateGame3.txt";
            break;
        default:
            break;
        }
    }

    else if (arg.argc == 3)
    {
        path_name = arg.argv[2];
    }
    else // argc = 6
    {
        path_name = arg.argv[3];
    }
    std::ifstream pattern(path_name);
    if (!pattern.is_open())
    {
        throw std::runtime_error("input file wasn't opened");
    }
    do
    {
        pattern >> this->uni_name;
    } while (uni_name != "#N");
    std::string line;
    getline(pattern, line);
    this->uni_name = line.substr(1, line.length() - 1);

    getline(pattern, line);
    int start_pos = line.find('B');
    int end_pos = line.find('/');
    this->birth_rule = line.substr(start_pos + 1, end_pos - start_pos - 1);
    this->survival_rule = line.substr(end_pos + 2, line.length() - end_pos);

    if (arg.argc != 1)
    {
        getline(pattern, line);
        start_pos = line.find(' ');
        line.replace(start_pos, 1, 1, '/');
        end_pos = line.find(' ');
        std::string h = line.substr(start_pos + 1, end_pos - start_pos);
        this->height = stoi(h);
        h = line.substr(end_pos + 1, line.length() - end_pos);
        this->width = stoi(h);
    }

    this->field = std::vector<std::vector<bool>>(width, std::vector<bool>(height, false));
    int x;
    int y;
    this->point_count = 0;
    while (!pattern.eof())
    {
        pattern >> x;
        pattern >> y;
        this->field[x - 1][y - 1] = ALIVE;
        this->point_count++;
    }
}

int Universe::size()
{
    return this->point_count;
}

int Universe::get_height()
{
    return this->height;
}

int Universe::get_width()
{
    return this->width;
}

std::string Universe::name()
{
    return this->uni_name;
}

std::string Universe::get_birth_rule()
{
    return this->birth_rule;
}

std::string Universe::get_survival_rule()
{
    return this->survival_rule;
}

bool Universe::at(int x, int y)
{
    if (x < 0 || x >= this->width || y < 0 || y >= this->height)
    {
        throw std::runtime_error("no such position on the field");
    }
    return this->field[x - 1][y - 1];
}

int LifeGame::count_neighbours(int x, int y)
{
    int count = 0;
    x = (x - 1 + this->width) % this->width;
    y = (y - 1 + this->height) % this->height;
    for (int i = 0; i < 3; i++)
    {
        if (this->field[(x - 1 + i + this->width) % this->width][(y + 1) % this->height])
            count++;
    }
    for (int i = 0; i < 3; i++)
    {
        if (this->field[(x - 1 + i + this->width) % this->width][(y - 1 + this->height) % this->height])
            count++;
    }
    if (this->field[(x - 1 + this->width) % this->width][y])
        count++;
    if (this->field[(x + 1) % this->width][y])
        count++;
    return count;
}

void LifeGame::display_universe()
{
    std::cout << " _";
    for (int i = 0; i < this->width; i++)
    {
        std::cout << "__";
    }
    std::cout << std::endl
              << "| ";
    for (int i = 0; i < this->width; i++)
    {
        for (int j = 0; j < this->height; j++)
        {
            if (this->field[j][i])
                std::cout << "O ";
            else
                std::cout << "- ";
        }
        std::cout << "|" << std::endl
                  << "| ";
    }
    for (int i = 0; i < this->width; i++)
    {
        std::cout << "__";
    }
    std::cout << "|" << std::endl
              << std::endl;
}

void LifeGame::write_universe(std::ofstream &write)
{

    write << " _";
    for (int i = 0; i < this->width; i++)
    {
        write << "__";
    }
    write << std::endl
          << "| ";
    for (int i = 0; i < this->width; i++)
    {
        for (int j = 0; j < this->height; j++)
        {
            if (this->field[i][j])
                write << "O ";
            else
                write << "- ";
        }
        write << "|" << std::endl
              << "| ";
    }
    for (int i = 0; i < this->width; i++)
    {
        write << "__";
    }
    write << "|" << std::endl
          << std::endl;
}

void LifeGame::game_step()
{
    int neighbours;
    std::vector<std::vector<bool>> copy(width, std::vector<bool>(height, false));
    copy = this->field;
    for (int i = 0; i < this->width; i++)
    {
        for (int j = 0; j < this->height; j++)
        {
            neighbours = this->count_neighbours(i + 1, j + 1);
            if (this->field[i][j] == false && this->check_birth_rule(i, j, neighbours, this->birth_rule) == true)
                copy[i][j] = true;

            else if (this->field[i][j] == true && this->check_survival_rule(j, j, neighbours, this->survival_rule) == false)
                copy[i][j] = false;
        }
    }
    this->field = copy;
}

bool LifeGame::check_birth_rule(int x, int y, int neighbours, std::string birth_rule)
{
    for (int i = 0; i < birth_rule.length(); i++)
    {
        if (neighbours == std::stoi(birth_rule.substr(i, 1)))
            return true;
    }
    return false;
}

bool LifeGame::check_survival_rule(int x, int y, int neighbours, std::string survival_rule)
{
    for (int i = 0; i < survival_rule.length(); i++)
    {
        if (neighbours == std::stoi(survival_rule.substr(i, 1)))
        {
            return true;
        }
    }
    return false;
}

void LifeGame::life_game(CmdLineArg &arg, std::ofstream &dump)
{
    std::string ans;
    if (dump.is_open())
    {
        this->write_universe(dump);
        for (int i = 0; i < this->iterations; i++)
        {
            dump << "iteration № " << i + 1 << std::endl;
            if ((i + 1) % 10 == 0 && i != 0)
            {
                std::cout << "Write 'exit' if you want to finish the game" << std::endl;
                std::cin >> ans;
                if (this->get_exit(ans))
                    break;
            }
            this->game_step();
            this->write_universe(dump);
        }
        return;
    }
    if (arg.argc < 6)
    {
        this->display_universe();
        for (int i = 0; i < this->iterations; i++)
        {
            std::cout << "iteration № " << i + 1 << std::endl;
            if ((i + 1) % 10 == 0 && i != 0)
            {
                std::cout << "Write 'exit' if you want to finish the game" << std::endl;
                std::cin >> ans;
                if (this->get_exit(ans))
                    break;
            }
            this->game_step();
            this->display_universe();
        }
    }
    else
    {
        std::ofstream write(arg.argv[5]);
        if (!write.is_open())
            throw std::runtime_error("output file wasn't opened");
        this->write_universe(write);
        for (int i = 0; i < this->iterations; i++)
        {
            std::cout << "iteration № " << i + 1 << std::endl;
            if ((i + 1) % 10 == 0 && i != 0)
            {
                std::cout << "Write 'exit' if you want to finish the game" << std::endl;
                std::cin >> ans;
                if (this->get_exit(ans))
                    break;
            }
            this->game_step();
            this->write_universe(write);
        }
    }
}

void LifeGame::get_help()
{
    std::cout << std::endl
              << "Write 'help' if you want to see the list of the comments or anything else to continue" << std::endl;
    std::string command;
    std::cin >> command;
    if (command == "help")
    {
        std::cout << std::endl
                  << "1. dump <filename> - save universe into a file" << std::endl
                  << "2. tick <n> (or just t <n>) - set number of iterations and print the result (n = 1 by default)" << std::endl
                  << "3. exit - finish the game" << std::endl
                  << std::endl;
    }
    else
        return;
}

std::ofstream LifeGame::get_dump(std::string command)
{
    std::ofstream write;
    if (command[0] == 'd')
    {
        std::string out_path;
        std::cin >> out_path;
        write.open(out_path);
        if (!write.is_open())
        {
            throw std::runtime_error("output file wasn't opened");
        }
    }
    return write;
}

void LifeGame::get_tick(std::string command, CmdLineArg &arg)
{
    if (command[0] == 't')
    {
        std::cin >> this->iterations;
    }
    if (arg.argc == 6 && command[0] == 't')
    {
        std::cout << "You already have the number of iterations in the list of arguments" << std::endl
                  << "Would you like to change it? Yes/No" << std::endl;
        std::string ans;
        std::cin >> ans;
        if (ans == "Yes")
            return;
    }
    if (arg.argc == 6)
    {
        this->iterations = atoi(arg.argv[1]);
    }
}

bool LifeGame::get_exit(std::string command)
{
    if (command == "exit")
        return true;
    return false;
}

/*
    x-1,y+1 x,y+1  x+1,y+1
     x-1,y   x,y    x+1,y
    x-1,y-1 x,y-1  x+1,y-1
*/
