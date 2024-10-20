#include <iostream>
#include <vector>
#include <fstream>
#include <exception>
#define FIELD_SIZE 15
enum point_state
{
    DEAD = 0,
    ALIVE = 1
};

class CmdLineArg
{
public:
    int argc = 0;
    char **argv = nullptr;
};

CmdLineArg &GetArguments();

class Universe
{
protected:
    std::vector<std::vector<bool>> field;
    int height;
    int width;
    std::string uni_name;
    std::string birth_rule;
    std::string survival_rule;
    int point_count;

public:
    Universe(CmdLineArg &arg, int mode = 0);
    int size(); // сколько точек на поле
    int get_height();
    int get_width();
    std::string name();
    std::string get_birth_rule();
    std::string get_survival_rule();
    bool at(int x, int y);
};

class LifeGame : public Universe
{
private:
    int iterations = 1;
public:
    LifeGame(CmdLineArg &arg, int mode = 0) : Universe(arg, mode){};
    void game_step();
    void life_game(CmdLineArg &arg, std::ofstream &dump);
    int count_neighbours(int x, int y);
    void display_universe();
    void write_universe(std::ofstream &write);
    bool check_birth_rule(int x, int y, int neighbours, std::string birth_rule);
    bool check_survival_rule(int x, int y, int neighbours, std::string survival_rule);
    void get_help();
    bool get_exit(std::string command);
    void get_tick(std::string command, CmdLineArg &arg);
    std::ofstream get_dump(std::string command);
};

/*

посмотреть возможность вводить несколько команд в начале игры

*/
