from utils import Event, EventStore

if __name__ == "__main__":
    events = EventStore()

    event_0 = Event("a", 0)
    event_1 = Event("b", 1)
    event_2 = Event("c", 1)
    event_3 = Event("a", 1)
    event_4 = Event("b", 2)
    event_5 = Event("a", 3)
    event_6 = Event("a", 3)
    event_7 = Event("a", 5)

    events.insert(event_0)
    events.insert(event_1)
    events.insert(event_2)
    events.insert(event_3)
    events.insert(event_4)
    events.insert(event_5)
    events.insert(event_6)
    events.insert(event_7)
    
    events.remove_all("c")
    events.remove_all("c")

    for node in events.query("a", 0, 5):
        print(node)

    for node in events.query("b", 0, 5):
        print(node)

