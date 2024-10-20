cmake_minimum_required(VERSION 3.6)
project(lab1)

include(FetchContent)
FetchContent_Declare(
  googletest
  # Specify the commit you depend on and update it regularly.
  URL https://github.com/google/googletest/archive/5376968f6948923e2411081fd9372e71a59d8e77.zip
)

# For Windows: Prevent overriding the parent project's compiler/linker settings
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)

add_executable(lab1 src/main.cpp)
target_sources(lab1 PRIVATE
    src/CircularBuffer.cpp
)
target_include_directories(lab1 PRIVATE
    include
)
target_link_libraries(lab1 gtest_main)
