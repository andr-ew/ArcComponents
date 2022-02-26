ArcNumberComponent {
    var <>arcDevice;
    var <val = 0;
    var <enc;
    var <>sens;
    var <>clamp;
    var <>func;
    var <>brightness = 15;

    *new { | arcDevice, enc = 0, sens = 1, clamp = true, func |
        ^super.new.init(arcDevice, enc, sens, clamp, func)
    }

    init { | arcDevice_, enc_, sens_, clamp_, func_ |
        arcDevice = arcDevice_;
        enc = enc_;
        sens = sens_;
        clamp = clamp_;
        func = func_;
    }

    redraw {
        arcDevice.ringall(enc, 0);
        arcDevice.ringset(enc, (val* 63).floor + 32 % 64, brightness);
    }

    delta { | n, d |
        if(n == enc, {
            var dScaled = (d * (1/sens/63));

            val = val + dScaled;

            if(clamp, {
                if(val > 1, { val = 1 });
                if(val < 0, { val = 0 });
            });

            func.value(val, dScaled);
            this.redraw();
        });
    }

    val_ { | newVal |
        val = newVal;
        this.redraw();
    }
}

ArcComponents {
    var <>arcDevice;
    var <>components;
    var oscFunc;

    *new { | arcDevice |
        ^super.new.init(arcDevice);
    }

    init { | arcDevice_ |
        components = Dictionary.new();

        arcDevice = arcDevice_;

        oscFunc = OSCFunc.newMatching({ arg msg, time, addr, recvPort;
            var n = msg[1], d = msg[2];

            components.do({ arg comp; comp.delta(n, d); });

        }, "/monome/enc/delta");
    }

    number { | enc, sens, clamp, func |
        components.put(
            enc,
            ArcNumberComponent.new(arcDevice, enc, sens, clamp, func)
        );

        components[enc].redraw;
    }

    setAt { | enc, newVal |
        components[enc].val = newVal;
    }

    getAt { | enc |
        ^components[enc].val
    }

    free {
        oscFunc.free;
        arcDevice.darkness;
    }
}