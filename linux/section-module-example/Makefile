PROGRAM := hello
SOURCES := $(wildcard *.c) 
OBJS := $(patsubst %.c,%.o,$(SOURCES))
LINKLIB  := 
LDFLAGS   := -Ts.lds

buildapp:clean subcompile linklds compile allcompile
	chmod 777 $(PROGRAM)

SUB_DIR-y :=
	
subcompile:
	@for dir in $(SUB_DIR-y) ; do \
	$(MAKE) -C $$dir sub_cmp; done
	
compile:
	$(CC) -c *.c $(LINKLIB) $(DBGFLAGS) $(INFLAGS)

linklds:
	${CC} -Wl,--verbose > s.lds 2>/dev/null; \
	sed -i '1,/==================================================/d' s.lds; \
	sed -i '/==================================================/,$$d' s.lds; \
	sed -i '/  __bss_start = .;/i  /* section hello */\n  .hello   : \n  {\n    _hello_start = .; \n    *(.hello) \n    _hello_end = .; \n  } \n  code_segment    : { *(code_segment) } \n' s.lds;
	
allcompile: $(PROGRAM)
$(PROGRAM): $(OBJS)
	$(CC) -g -o $@ $^ $(LINKLIB) $(LDFLAGS)

	
clean:
	rm -f *.o $(PROGRAM)
