#include <unistd.h>
#include <stdint.h>
#include <stdio.h>

typedef struct hello_s {
    char *name;
    void (*func)(void);
} hello_t;

extern hello_t _hello_start[];
extern hello_t _hello_end[];

#define _init __attribute__((unused, section(".hello")))
#define module_init(name, func) \
    static const hello_t hello_##func _init = {name, func}

static void module_1_init(void)
{
    printf("module 1 init\n");
}

static void module_2_init(void)
{
    printf("module 2 init\n");
}

module_init("module_1", module_1_init);
module_init("module_2", module_2_init);

int main(void)
{
    hello_t *call_ptr = _hello_start;
    do {
            printf("call module %s %p\n", call_ptr->name, call_ptr->func);
            (*call_ptr->func)();
            ++call_ptr;
    } while (call_ptr < _hello_end);
	return 0;
}
