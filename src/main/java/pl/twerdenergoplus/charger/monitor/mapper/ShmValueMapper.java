package pl.twerdenergoplus.charger.monitor.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps raw SHM int32 values to human-readable labels for known enum/flag signals.
 * Based on the grafana-shm-mapper.md reference document.
 * <p>Coverage:
 * inverterDataToSend[0]=ccsState, inverterDataReceived[0]=evseStatus,
 * evsGroup3[0]=g_enableCharge, evsValuesToUpdate[0,1,6],
 * evsValuesToUpdateFromModbusTCP[0,1], additionalGroup[0,1],
 * errors[0]=modbusErrorCode, maxOutput[5]=contactor.
 */
public final class ShmValueMapper {

    // shmFileName -> index (as string) -> rawIntValue -> label
    private static final Map<String, Map<String, Map<Integer, String>>> MAPPINGS = new HashMap<>();

    static {

        // ── inverterDataToSend / index 0 — ccsState ────────────────────────
        Map<Integer, String> ccsState = new HashMap<>();
        ccsState.put(0, "Idle");
        ccsState.put(2, "CableCheck");
        ccsState.put(3, "Precharge");
        ccsState.put(5, "CurrentDemand");
        ccsState.put(6, "PowerDeliveryStop");
        addMapping("inverterDataToSend", "0", ccsState);

        // ── inverterDataReceived / index 0 — evseStatus ────────────────────
        Map<Integer, String> evseStatus = new HashMap<>();
        evseStatus.put(0, "NotReady");
        evseStatus.put(1, "ReadyToCharge");
        evseStatus.put(2, "ChargingInProgress");
        evseStatus.put(4, "ShutdownRequested");
        evseStatus.put(6, "MalfunctionFault");
        addMapping("inverterDataReceived", "0", evseStatus);

        // ── evsGroup3 / index 0 — g_enableCharge ───────────────────────────
        Map<Integer, String> enableCharge = new HashMap<>();
        enableCharge.put(0, "Disabled");
        enableCharge.put(1, "Enabled");
        addMapping("evsGroup3", "0", enableCharge);

        // evsGroup4 / index 27
        Map<Integer, String> ccsPwmState = new HashMap<>();
        ccsPwmState.put(0, "zero");
        ccsPwmState.put(1, "unplugged");
        ccsPwmState.put(2, "plugged in");
        ccsPwmState.put(3, "charging");
        ccsPwmState.put(4, "charging");
        addMapping("evsGroup4", "27", ccsPwmState);

        // evsGroup4 / index 50
        Map<Integer, String> shademoPwmState = new HashMap<>();
        shademoPwmState.put(0, "zero");
        shademoPwmState.put(1, "unplugged");
        shademoPwmState.put(2, "plugged in");
        shademoPwmState.put(3, "charging");
        shademoPwmState.put(4, "charging");
        addMapping("evsGroup4", "50", shademoPwmState);

        // ── evsValuesToUpdate / index 0 — enableChargeSHM ──────────────────
        Map<Integer, String> enableChargeSHM = new HashMap<>();
        enableChargeSHM.put(0, "Stop");
        enableChargeSHM.put(1, "Start");
        enableChargeSHM.put(10, "KeepCurrentState");
        addMapping("evsValuesToUpdate", "0", enableChargeSHM);

        // ── evsValuesToUpdate / index 1 — enableACChargeSHM ────────────────
        Map<Integer, String> enableACChargeSHM = new HashMap<>();
        enableACChargeSHM.put(0, "Disabled");
        enableACChargeSHM.put(1, "Enabled");
        addMapping("evsValuesToUpdate", "1", enableACChargeSHM);

        // ── evsValuesToUpdate / index 6 — trybV2G ──────────────────────────
        Map<Integer, String> trybV2G = new HashMap<>();
        trybV2G.put(0, "Normal");
        trybV2G.put(1, "V2G");
        addMapping("evsValuesToUpdate", "6", trybV2G);

        // ── evsValuesToUpdateFromModbusTCP / index 0 — enableChargeModbus ──
        Map<Integer, String> enableChargeModbus = new HashMap<>();
        enableChargeModbus.put(0, "Stop");
        enableChargeModbus.put(1, "Start");
        enableChargeModbus.put(10, "KeepCurrentState");
        addMapping("evsValuesToUpdateFromModbusTCP", "0", enableChargeModbus);

        // ── evsValuesToUpdateFromModbusTCP / index 1 — enableACChargeModbus ─
        Map<Integer, String> enableACChargeModbus = new HashMap<>();
        enableACChargeModbus.put(0, "Disabled");
        enableACChargeModbus.put(1, "Enabled");
        addMapping("evsValuesToUpdateFromModbusTCP", "1", enableACChargeModbus);

        // ── additionalGroup / index 0 — emergencyButton ─────────────────────
        Map<Integer, String> emergencyButton = new HashMap<>();
        emergencyButton.put(0, "Inactive");
        emergencyButton.put(1, "EmergencyStopPressed");
        addMapping("additionalGroup", "0", emergencyButton);

        // ── additionalGroup / index 1 — benderIsolationFault ────────────────
        Map<Integer, String> benderFault = new HashMap<>();
        benderFault.put(0, "OK");
        benderFault.put(1, "IsolationFault");
        addMapping("additionalGroup", "1", benderFault);

        // ── errors / index 0 — modbusErrorCode ──────────────────────────────
        // 0 = OK; any other value is a libmodbus error code (no fixed label)
        Map<Integer, String> modbusError = new HashMap<>();
        modbusError.put(0, "OK");
        addMapping("errors", "0", modbusError);

        // ── maxOutput / index 5 — CHAdeMO DC / AC charging contactor ─────────
        Map<Integer, String> contactor = new HashMap<>();
        contactor.put(0, "ContactorOpen");
        contactor.put(1, "ContactorClosed");
        addMapping("maxOutput", "5", contactor);
    }

    // ────────────────────────────────────────────────────────────────────────

    private static void addMapping(String shmFileName, String index, Map<Integer, String> values) {
        MAPPINGS.computeIfAbsent(shmFileName, k -> new HashMap<>()).put(index, values);
    }

    /**
     * Returns a human-readable label for the given SHM file, index and raw integer value,
     * or {@code null} when no mapping is defined for that combination.
     *
     * @param shmFileName SHM file name (e.g. "inverterDataToSend")
     * @param index       zero-based element index as a string (e.g. "0")
     * @param value       raw int32 value
     * @return label string, or {@code null}
     */
    public static String resolve(String shmFileName, String index, int value) {
        Map<String, Map<Integer, String>> byIndex = MAPPINGS.get(shmFileName);
        if (byIndex == null) {
            return null;
        }
        Map<Integer, String> byValue = byIndex.get(index);
        if (byValue == null) {
            return null;
        }
        return byValue.get(value);
    }

    private ShmValueMapper() {
        // utility class — do not instantiate
    }
}


